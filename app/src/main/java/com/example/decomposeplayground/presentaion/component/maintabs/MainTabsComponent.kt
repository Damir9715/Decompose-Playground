package com.example.decomposeplayground.presentaion.component.maintabs

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.example.decomposeplayground.presentaion.component.cabinet.CabinetComponent
import com.example.decomposeplayground.presentaion.component.cabinet.CabinetComponentImpl
import com.example.decomposeplayground.presentaion.component.favorites.FavoritesComponent
import com.example.decomposeplayground.presentaion.component.favorites.FavoritesComponentImpl
import com.example.decomposeplayground.presentaion.component.listing.ListingComponent
import com.example.decomposeplayground.presentaion.component.listing.ListingComponentImpl
import com.example.decomposeplayground.presentaion.component.messages.MessagesComponent
import com.example.decomposeplayground.presentaion.component.messages.MessagesComponentImpl
import kotlinx.parcelize.Parcelize

interface MainTabsComponent {

    val childStack: Value<ChildStack<*, Child>>

    fun onAdvertListTabClicked()
    fun onFavoritesTabClicked()
    fun onPostAdvertTabClicked()
    fun onMessagesTabClicked()
    fun onCabinetTabClicked()

    sealed interface Child {

        data class AdvertListChild(val component: ListingComponent) : Child
        data class FavoritesChild(val component: FavoritesComponent) : Child
        data class MessagesChild(val component: MessagesComponent) : Child
        data class CabinetChild(val component: CabinetComponent) : Child
    }
}

class MainTabsComponentImpl(
        componentContext: ComponentContext,
        private val onPostAdvertTabClicked: () -> Unit,
) : MainTabsComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()

    override val childStack: Value<ChildStack<*, MainTabsComponent.Child>> =
            childStack(
                    source = navigation,
                    initialConfiguration = Config.AdvertList,
                    handleBackButton = true,
                    childFactory = ::child,
            )

    private fun child(config: Config, componentContext: ComponentContext): MainTabsComponent.Child =
            when (config) {
                is Config.AdvertList -> MainTabsComponent.Child.AdvertListChild(
                        component = ListingComponentImpl(
                                componentContext = componentContext,
                        )
                )
                is Config.Favorites -> MainTabsComponent.Child.FavoritesChild(
                        component = FavoritesComponentImpl(
                                componentContext = componentContext
                        )
                )
                is Config.Cabinet -> MainTabsComponent.Child.CabinetChild(
                        component = CabinetComponentImpl(
                                componentContext = componentContext
                        )
                )
                is Config.Messages -> MainTabsComponent.Child.MessagesChild(
                        component = MessagesComponentImpl(
                                componentContext = componentContext
                        )
                )
            }

    override fun onAdvertListTabClicked() {
        navigation.bringToFront(Config.AdvertList)
    }

    override fun onFavoritesTabClicked() {
        navigation.bringToFront(Config.Favorites)
    }

    override fun onPostAdvertTabClicked() {
        onPostAdvertTabClicked.invoke()
    }

    override fun onMessagesTabClicked() {
        navigation.bringToFront(Config.Messages)
    }

    override fun onCabinetTabClicked() {
        navigation.bringToFront(Config.Cabinet)
    }

    private sealed interface Config : Parcelable {

        @Parcelize
        data object AdvertList : Config

        @Parcelize
        data object Favorites : Config

        @Parcelize
        data object Messages : Config

        @Parcelize
        data object Cabinet : Config
    }
}