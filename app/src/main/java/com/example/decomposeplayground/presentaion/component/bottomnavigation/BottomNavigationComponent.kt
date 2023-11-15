package com.example.decomposeplayground.presentaion.component.bottomnavigation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.arkivanov.essenty.parcelable.Parcelable
import com.example.decomposeplayground.data.database.AdvertsDatabase
import com.example.decomposeplayground.presentaion.component.cabinetscope.CabinetScopeComponent
import com.example.decomposeplayground.presentaion.component.cabinetscope.CabinetScopeComponentImpl
import com.example.decomposeplayground.presentaion.component.favorites.FavoritesComponent
import com.example.decomposeplayground.presentaion.component.favorites.FavoritesComponentImpl
import com.example.decomposeplayground.presentaion.component.listingscope.ListingScopeComponent
import com.example.decomposeplayground.presentaion.component.listingscope.ListingScopeComponentImpl
import com.example.decomposeplayground.presentaion.component.messages.MessagesComponent
import com.example.decomposeplayground.presentaion.component.messages.MessagesComponentImpl
import kotlinx.parcelize.Parcelize

interface BottomNavigationComponent {

    val state: Value<State>

    val childStack: Value<ChildStack<*, Child>>

    fun onAdvertListTabClicked()
    fun onFavoritesTabClicked()
    fun onPostAdvertTabClicked()
    fun onMessagesTabClicked()
    fun onCabinetTabClicked()

    fun showBottomNavigation()
    fun hideBottomNavigation()

    data class State(val isBottomNavigationVisible: Boolean)

    sealed interface Child {

        data class AdvertListChild(val component: ListingScopeComponent) : Child
        data class FavoritesChild(val component: FavoritesComponent) : Child
        data class MessagesChild(val component: MessagesComponent) : Child
        data class CabinetChild(val component: CabinetScopeComponent) : Child
    }
}

class BottomNavigationComponentImpl(
        componentContext: ComponentContext,
        private val database: AdvertsDatabase,
        private val onPostAdvertTabClicked: () -> Unit,
) : BottomNavigationComponent, ComponentContext by componentContext {

    private var _state = MutableValue(BottomNavigationComponent.State(false))

    override val state: Value<BottomNavigationComponent.State> = _state

    private val navigation = StackNavigation<Config>()

    override val childStack: Value<ChildStack<*, BottomNavigationComponent.Child>> =
            childStack(
                    source = navigation,
                    initialConfiguration = Config.AdvertList,
                    handleBackButton = true,
                    childFactory = ::child,
            )

    private fun child(config: Config, componentContext: ComponentContext): BottomNavigationComponent.Child =
            when (config) {
                is Config.AdvertList -> BottomNavigationComponent.Child.AdvertListChild(
                        component = ListingScopeComponentImpl(
                                componentContext = componentContext,
                                database = database,
                                showBottomNavigation = ::showBottomNavigation,
                                hideBottomNavigation = ::hideBottomNavigation,
                        )
                )
                is Config.Favorites -> BottomNavigationComponent.Child.FavoritesChild(
                        component = FavoritesComponentImpl(
                                componentContext = componentContext
                        )
                )
                is Config.Cabinet -> BottomNavigationComponent.Child.CabinetChild(
                        component = CabinetScopeComponentImpl(
                                componentContext = componentContext
                        )
                )
                is Config.Messages -> BottomNavigationComponent.Child.MessagesChild(
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

    override fun showBottomNavigation() {
        _state.update { _state.value.copy(isBottomNavigationVisible = true) }
    }

    override fun hideBottomNavigation() {
        _state.update { _state.value.copy(isBottomNavigationVisible = false) }
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