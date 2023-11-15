package com.example.decomposeplayground.presentaion.component.listing

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.example.decomposeplayground.data.database.AdvertsDatabase
import com.example.decomposeplayground.presentaion.component.advertlist.AdvertListComponent
import com.example.decomposeplayground.presentaion.component.advertlist.AdvertListComponentImpl
import kotlinx.parcelize.Parcelize

interface ListingComponent {

    val childStack: Value<ChildStack<*, Child>>

    fun onAdvertClicked(id: Long)

    sealed interface Child {

        data class AdvertList(val component: AdvertListComponent) : Child
    }
}

class ListingComponentImpl(
        componentContext: ComponentContext,
        private val database: AdvertsDatabase,
        private val onAdvertClicked: (Long) -> Unit,
        private val onFilterClicked: () -> Unit,
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
        onAdvertClicked.invoke(id)
    }

    private fun child(config: Config, componentContext: ComponentContext): ListingComponent.Child =
            when (config) {
                is Config.AdvertList -> ListingComponent.Child.AdvertList(
                        component = AdvertListComponentImpl(
                                componentContext = componentContext,
                                database = database,
                                onAdvertClicked = ::onAdvertClicked,
                                onFilterClicked = onFilterClicked,
                        )
                )
            }

    private sealed interface Config : Parcelable {

        @Parcelize
        data object AdvertList : Config
    }
}