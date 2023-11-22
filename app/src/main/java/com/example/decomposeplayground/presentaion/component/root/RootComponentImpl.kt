package com.example.decomposeplayground.presentaion.component.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.arkivanov.essenty.backhandler.BackCallback
import com.arkivanov.essenty.parcelable.Parcelable
import com.example.decomposeplayground.data.database.DefaultAdvertsDatabase
import com.example.decomposeplayground.presentaion.MainActivity
import com.example.decomposeplayground.presentaion.component.advertdetails.AdvertDetailsComponentImpl
import com.example.decomposeplayground.presentaion.component.bottomnavigation.BottomNavigationComponentImpl
import com.example.decomposeplayground.presentaion.component.postadvert.PostAdvertComponentImpl
import kotlinx.parcelize.Parcelize

private const val EXIT_APPLICATION_TIMEOUT = 2000

class RootComponentImpl(
        componentContext: ComponentContext,
        private val activity: MainActivity,
) : RootComponent, ComponentContext by componentContext {

    private var backPressedTime = 0L

    private val database = DefaultAdvertsDatabase()

    private val _state = MutableValue(RootComponent.State(null))
    override val state: Value<RootComponent.State> = _state

    //stack navigation
    private val navigation = StackNavigation<Config>()
    override val childStack: Value<ChildStack<*, RootComponent.Child>> =
            childStack(
                    source = navigation,
                    initialConfiguration = Config.BottomNavigation,
                    handleBackButton = true,
                    childFactory = ::child,
            )

    private fun child(config: Config, componentContext: ComponentContext): RootComponent.Child =
            when (config) {
                is Config.BottomNavigation -> RootComponent.Child.BottomNavigationChild(
                        component = BottomNavigationComponentImpl(
                                componentContext = componentContext,
                                database = database,
                                onPostAdvertTabClicked = ::onPostAdvertTabClicked,
                                setBackCallback = ::setBackCallback,
                                onAdvertClicked = ::onAdvertClicked,
                        )
                )
                is Config.PostAdvert -> RootComponent.Child.PostAdvertChild(
                        component = PostAdvertComponentImpl(
                                componentContext = componentContext
                        )
                )
                is Config.AdvertDetails -> RootComponent.Child.AdvertDetailsChild(
                        component = AdvertDetailsComponentImpl(
                                componentContext = componentContext,
                                database = database,
                                advertId = config.id,
                                onFinished = ::onAdvertDetailsCloseClicked,
                        )
                )
            }

    //back callback
    private val backCallback = BackCallback {
        if (backPressedTime + EXIT_APPLICATION_TIMEOUT > System.currentTimeMillis()) {
            activity.finish()

            return@BackCallback
        }

        _state.update { _state.value.copy(toast = "Чтобы выйти из приложения, нажмите НАЗАД еще раз") }
        backPressedTime = System.currentTimeMillis()
    }

    init {
        backHandler.register(backCallback)
    }

    override fun onPostAdvertTabClicked() {
        navigation.push(Config.PostAdvert)
    }

    override fun onToastShown() {
        _state.update { _state.value.copy(toast = null) }
    }

    override fun onAdvertClicked(id: Long) {
        navigation.push(Config.AdvertDetails(id))
    }

    override fun setBackCallback(isEnabled: Boolean) {
        backCallback.isEnabled = isEnabled
    }

    override fun onAdvertDetailsCloseClicked() {
        navigation.pop()
    }

    private sealed interface Config : Parcelable {

        @Parcelize
        data object BottomNavigation : Config

        @Parcelize
        data object PostAdvert : Config

        @Parcelize
        data class AdvertDetails(val id: Long) : Config
    }
}