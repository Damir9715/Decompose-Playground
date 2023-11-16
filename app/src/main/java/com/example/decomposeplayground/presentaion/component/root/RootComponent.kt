package com.example.decomposeplayground.presentaion.component.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.arkivanov.essenty.backhandler.BackCallback
import com.arkivanov.essenty.parcelable.Parcelable
import com.example.decomposeplayground.presentaion.MainActivity
import com.example.decomposeplayground.presentaion.component.host.HostComponent
import com.example.decomposeplayground.presentaion.component.host.HostComponentImpl
import kotlinx.parcelize.Parcelize

private const val EXIT_APPLICATION_TIMEOUT = 2000

interface RootComponent {

    val state: Value<State>

    val childStack: Value<ChildStack<*, Child>>

    fun onToastShown()

    data class State(val toast: String? = null)

    sealed interface Child {
        data class RootChild(val component: HostComponent) : Child
    }
}

class RootComponentImpl(
        componentContext: ComponentContext,
        private val activity: MainActivity,
) : RootComponent, ComponentContext by componentContext {

    private var backPressedTime = 0L

    private val _state = MutableValue(RootComponent.State(null))
    override val state: Value<RootComponent.State> = _state

    //stack navigation
    private val navigation = StackNavigation<Config>()
    override val childStack: Value<ChildStack<*, RootComponent.Child>> =
            childStack(
                    source = navigation,
                    initialConfiguration = Config.Root,
                    handleBackButton = false,
                    childFactory = ::child,
            )

    private fun child(config: Config, componentContext: ComponentContext): RootComponent.Child =
            when (config) {
                is Config.Root -> RootComponent.Child.RootChild(
                        component = HostComponentImpl(
                                componentContext = componentContext,
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

    override fun onToastShown() {
        _state.update { _state.value.copy(toast = null) }
    }

    private sealed interface Config : Parcelable {

        @Parcelize
        data object Root : Config
    }
}