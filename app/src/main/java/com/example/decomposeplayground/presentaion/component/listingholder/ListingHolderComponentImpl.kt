package com.example.decomposeplayground.presentaion.component.listingholder

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.navigate
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.example.decomposeplayground.domain.usecase.GetAdvertListUseCase
import com.example.decomposeplayground.presentaion.component.advertlist.AdvertListComponentImpl
import com.example.decomposeplayground.presentaion.component.filter.FilterComponentImpl
import kotlinx.parcelize.Parcelize

class ListingHolderComponentImpl(
        componentContext: ComponentContext,
        private val getAdvertListUseCase: GetAdvertListUseCase,
        private val setBottomNavigationVisibility: (Boolean) -> Unit,
        private val onAdvertClicked: (Long) -> Unit,
) : ListingHolderComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()
    override val childStack: Value<ChildStack<*, ListingHolderComponent.Child>> =
            childStack(
                    source = navigation,
                    initialConfiguration = Config.AdvertList(),
                    handleBackButton = true,
                    childFactory = ::child,
            )

    private fun child(config: Config, componentContext: ComponentContext): ListingHolderComponent.Child =
            when (config) {
                is Config.AdvertList -> ListingHolderComponent.Child.AdvertListChild(
                        component = AdvertListComponentImpl(
                                componentContext = componentContext,
                                getAdvertListUseCase = getAdvertListUseCase,
                                sqb = config.sqb,
                                onAdvertClicked = ::onAdvertClicked,
                                onFilterClicked = ::onFilterClicked,
                                setBottomNavigationVisibility = setBottomNavigationVisibility,
                        )
                )
                is Config.Filter -> ListingHolderComponent.Child.FilterChild(
                        component = FilterComponentImpl(
                                componentContext = componentContext,
                                setBottomNavigationVisibility = setBottomNavigationVisibility,
                                onFilterApplied = ::onFilterApplied,
                                sqb = config.sqb,
                        )
                )
            }

    override fun onAdvertClicked(id: Long) {
        onAdvertClicked.invoke(id)
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

    private sealed interface Config : Parcelable {

        @Parcelize
        data class AdvertList(val sqb: Int = 0) : Config

        @Parcelize
        data class Filter(val sqb: Int = 0) : Config
    }
}