package com.example.decomposeplayground.presentaion.component.listingholder

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.navigate
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.example.decomposeplayground.data.database.DefaultAdvertsDatabase
import com.example.decomposeplayground.presentaion.component.advertdetails.AdvertDetailsComponent
import com.example.decomposeplayground.presentaion.component.advertdetails.AdvertDetailsComponentImpl
import com.example.decomposeplayground.presentaion.component.advertlist.AdvertListComponent
import com.example.decomposeplayground.presentaion.component.advertlist.AdvertListComponentImpl
import com.example.decomposeplayground.presentaion.component.filter.FilterComponent
import com.example.decomposeplayground.presentaion.component.filter.FilterComponentImpl
import kotlinx.parcelize.Parcelize

interface ListingHolderComponent {

    val childStack: Value<ChildStack<*, Child>>

    fun onAdvertClicked(id: Long)

    fun onAdvertDetailsCloseClicked()

    fun onFilterClicked(sqb: Int)

    fun onFilterApplied(sqb: Int)

    sealed interface Child {

        data class AdvertList(val component: AdvertListComponent) : Child
        data class AdvertDetails(val component: AdvertDetailsComponent) : Child
        data class Filter(val component: FilterComponent) : Child
    }
}

class ListingHolderComponentImpl(
        componentContext: ComponentContext,
        private val showBottomNavigation: () -> Unit,
        private val hideBottomNavigation: () -> Unit,
) : ListingHolderComponent, ComponentContext by componentContext {

    private val database = DefaultAdvertsDatabase()

    private val navigation = StackNavigation<Config>()

    override val childStack: Value<ChildStack<*, ListingHolderComponent.Child>> =
            childStack(
                    source = navigation,
                    initialConfiguration = Config.AdvertList(),
                    handleBackButton = true,
                    childFactory = ::child,
            )

    override fun onAdvertClicked(id: Long) {
        navigation.push(Config.AdvertDetails(id))
    }

    override fun onAdvertDetailsCloseClicked() {
        navigation.pop()
    }

    override fun onFilterClicked(sqb: Int) {
        navigation.push(Config.Filter(sqb))
    }

    override fun onFilterApplied(sqb: Int) {
        navigation.navigate { stack ->
            stack
                    //удаляем все выдачи кроме первой
                    .filterNot { it is Config.AdvertList && it.sqb != 0 }
                    //удаляем все фильтры кроме последней
                    .filterNot { it is Config.Filter && it.sqb != sqb - 1 }
        }
        navigation.push(Config.AdvertList(sqb))
    }

    private fun child(config: Config, componentContext: ComponentContext): ListingHolderComponent.Child =
            when (config) {
                is Config.AdvertList -> ListingHolderComponent.Child.AdvertList(
                        component = AdvertListComponentImpl(
                                componentContext = componentContext,
                                database = database,
                                onAdvertClicked = ::onAdvertClicked,
                                onFilterClicked = ::onFilterClicked,
                                showBottomNavigation = showBottomNavigation,
                                sqb = config.sqb,
                        )
                )
                is Config.AdvertDetails -> ListingHolderComponent.Child.AdvertDetails(
                        component = AdvertDetailsComponentImpl(
                                componentContext = componentContext,
                                database = database,
                                advertId = config.id,
                                onFinished = ::onAdvertDetailsCloseClicked,
                                hideBottomNavigation = hideBottomNavigation,
                        )
                )
                is Config.Filter -> ListingHolderComponent.Child.Filter(
                        component = FilterComponentImpl(
                                componentContext = componentContext,
                                hideBottomNavigation = hideBottomNavigation,
                                onFilterApplied = ::onFilterApplied,
                                sqb = config.sqb,
                        )
                )
            }

    private sealed interface Config : Parcelable {

        @Parcelize
        data class AdvertList(val sqb: Int = 0) : Config

        @Parcelize
        data class AdvertDetails(val id: Long) : Config

        @Parcelize
        data class Filter(val sqb: Int = 0) : Config
    }
}