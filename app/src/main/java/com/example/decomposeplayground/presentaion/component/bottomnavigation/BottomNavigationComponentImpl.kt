package com.example.decomposeplayground.presentaion.component.bottomnavigation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.arkivanov.essenty.lifecycle.Lifecycle
import com.arkivanov.essenty.parcelable.Parcelable
import com.example.decomposeplayground.data.database.AdvertsDatabase
import com.example.decomposeplayground.presentaion.component.cabinetholder.CabinetHolderComponentImpl
import com.example.decomposeplayground.presentaion.component.favorites.FavoritesComponentImpl
import com.example.decomposeplayground.presentaion.component.listingholder.ListingHolderComponentImpl
import com.example.decomposeplayground.presentaion.component.messages.MessagesComponentImpl
import kotlinx.parcelize.Parcelize

class BottomNavigationComponentImpl(
        componentContext: ComponentContext,
        private val database: AdvertsDatabase,
        private val onPostAdvertTabClicked: () -> Unit,
        private val onAdvertClicked: (Long) -> Unit,
        private val setBackCallback: (Boolean) -> Unit,
) : BottomNavigationComponent, ComponentContext by componentContext {

    private var _state = MutableValue(BottomNavigationComponent.State(true))
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
                        component = ListingHolderComponentImpl(
                                componentContext = componentContext,
                                database = database,
                                setBottomNavigationVisibility = ::setBottomNavigationVisibility,
                                onAdvertClicked = onAdvertClicked,
                        )
                )
                is Config.Favorites -> BottomNavigationComponent.Child.FavoritesChild(
                        component = FavoritesComponentImpl(
                                componentContext = componentContext,
                        )
                )
                is Config.Cabinet -> BottomNavigationComponent.Child.CabinetChild(
                        component = CabinetHolderComponentImpl(
                                componentContext = componentContext,
                        )
                )
                is Config.Messages -> BottomNavigationComponent.Child.MessagesChild(
                        component = MessagesComponentImpl(
                                componentContext = componentContext,
                        )
                )
            }

    init {
        lifecycle.subscribe(callbacks = object : Lifecycle.Callbacks {
            override fun onStart() {
                super.onStart()
                setBackCallback.invoke(true)
            }

            override fun onStop() {
                super.onStop()
                setBackCallback.invoke(false)
            }
        })
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

    override fun setBottomNavigationVisibility(isVisible: Boolean) {
        _state.update { _state.value.copy(isBottomNavigationVisible = isVisible) }
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