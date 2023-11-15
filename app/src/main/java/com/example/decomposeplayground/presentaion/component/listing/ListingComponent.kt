package com.example.decomposeplayground.presentaion.component.listing

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.example.decomposeplayground.data.database.AdvertsDatabase
import com.example.decomposeplayground.presentaion.component.advertdetails.AdvertDetailsComponent
import com.example.decomposeplayground.presentaion.component.advertdetails.AdvertDetailsComponentImpl
import com.example.decomposeplayground.presentaion.component.advertlist.AdvertListComponent
import com.example.decomposeplayground.presentaion.component.advertlist.AdvertListComponentImpl
import com.example.decomposeplayground.presentaion.component.filter.FilterComponent
import com.example.decomposeplayground.presentaion.component.filter.FilterComponentImpl
import kotlinx.parcelize.Parcelize

interface ListingComponent {

    val childStack: Value<ChildStack<*, Child>>

    fun onAdvertClicked(id: Long)

    fun onAdvertDetailsCloseClicked()

    fun onFilterClicked()

    sealed interface Child {

        data class AdvertList(val component: AdvertListComponent) : Child
        data class AdvertDetails(val component: AdvertDetailsComponent) : Child
        data class Filter(val component: FilterComponent) : Child
    }
}

class ListingComponentImpl(
        componentContext: ComponentContext,
        private val database: AdvertsDatabase,
        private val showBottomNavigation: () -> Unit,
        private val hideBottomNavigation: () -> Unit,
) : ListingComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()

    override val childStack: Value<ChildStack<*, ListingComponent.Child>> =
            childStack(
                    source = navigation,
                    initialConfiguration = Config.AdvertList,
                    handleBackButton = true,
                    childFactory = ::child,
            )

    override fun onAdvertClicked(id: Long) {
        navigation.push(Config.AdvertDetails(id))
    }

    override fun onAdvertDetailsCloseClicked() {
        navigation.pop()
    }

    override fun onFilterClicked() {
        navigation.push(Config.Filter)
    }

    private fun child(config: Config, componentContext: ComponentContext): ListingComponent.Child =
            when (config) {
                is Config.AdvertList -> ListingComponent.Child.AdvertList(
                        component = AdvertListComponentImpl(
                                componentContext = componentContext,
                                database = database,
                                onAdvertClicked = ::onAdvertClicked,
                                onFilterClicked = ::onFilterClicked,
                                showBottomNavigation = showBottomNavigation,
                        )
                )
                is Config.AdvertDetails -> ListingComponent.Child.AdvertDetails(
                        component = AdvertDetailsComponentImpl(
                                componentContext = componentContext,
                                database = database,
                                advertId = config.id,
                                onFinished = ::onAdvertDetailsCloseClicked,
                                hideBottomNavigation = hideBottomNavigation,
                        )
                )
                is Config.Filter -> ListingComponent.Child.Filter(
                        component = FilterComponentImpl(
                                componentContext = componentContext,
                                hideBottomNavigation = hideBottomNavigation,
                        )
                )
            }

    private sealed interface Config : Parcelable {

        @Parcelize
        data object AdvertList : Config

        @Parcelize
        data class AdvertDetails(val id: Long) : Config

        @Parcelize
        data object Filter : Config
    }
}