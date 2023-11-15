package com.example.decomposeplayground.presentaion.component.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.router.slot.dismiss
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.backhandler.BackCallback
import com.arkivanov.essenty.parcelable.Parcelable
import com.example.decomposeplayground.presentaion.component.bottomnavigation.BottomNavigationComponent
import com.example.decomposeplayground.presentaion.component.bottomnavigation.BottomNavigationComponentImpl
import com.example.decomposeplayground.presentaion.component.exitdialog.ExitDialogComponent
import com.example.decomposeplayground.presentaion.component.exitdialog.ExitDialogComponentImpl
import com.example.decomposeplayground.presentaion.component.postadvert.PostAdvertComponent
import com.example.decomposeplayground.presentaion.component.postadvert.PostAdvertComponentImpl
import kotlinx.parcelize.Parcelize

interface RootComponent {

    val childStack: Value<ChildStack<*, Child>>

    val dialogSlot: Value<ChildSlot<*, ExitDialogComponent>>

    fun onPostAdvertTabClicked()

    fun onExitDialogDismissed()

    fun onExitDialogConfirmed()

    sealed interface Child {

        data class MainTabsChild(val component: BottomNavigationComponent) : Child
        data class PostAdvertChild(val component: PostAdvertComponent) : Child
    }
}

class RootComponentImpl(
        componentContext: ComponentContext,
) : RootComponent, ComponentContext by componentContext {

    //stack navigation
    private val navigation = StackNavigation<Config>()
    override val childStack: Value<ChildStack<*, RootComponent.Child>> =
            childStack(
                    source = navigation,
                    initialConfiguration = Config.MainTabs,
                    handleBackButton = false,
                    childFactory = ::child,
            )

    private fun child(config: Config, componentContext: ComponentContext): RootComponent.Child =
            when (config) {
                is Config.MainTabs -> RootComponent.Child.MainTabsChild(
                        component = BottomNavigationComponentImpl(
                                componentContext = componentContext,
                                onPostAdvertTabClicked = ::onPostAdvertTabClicked,
                        )
                )
                is Config.PostAdvert -> RootComponent.Child.PostAdvertChild(
                        component = PostAdvertComponentImpl(
                                componentContext = componentContext
                        )
                )
            }

    //dialog navigation
    private val dialogNavigation = SlotNavigation<DialogConfig>()
    private val _dialogSlot =
            childSlot<DialogConfig, ExitDialogComponent>(
                    source = dialogNavigation,
                    handleBackButton = true,
                    childFactory = { _, _ ->
                        ExitDialogComponentImpl(
                                onDismissed = ::onExitDialogDismissed,
                                onConfirmed = ::onExitDialogConfirmed,
                        )
                    }
            )
    override val dialogSlot: Value<ChildSlot<*, ExitDialogComponent>> = _dialogSlot

    //back callback
    private val backCallback = BackCallback {
        dialogNavigation.activate(DialogConfig)
    }

    init {
        backHandler.register(backCallback)
    }

    override fun onPostAdvertTabClicked() {
        navigation.push(Config.PostAdvert)
    }

    override fun onExitDialogDismissed() {
        dialogNavigation.dismiss()
    }

    override fun onExitDialogConfirmed() {
        dialogNavigation.dismiss()
//        backHandler.unregister(backCallback)
//        backCallback.isEnabled = false
        backCallback.onBack()
        //todo click back button
    }

    private sealed interface Config : Parcelable {

        @Parcelize
        data object MainTabs : Config

        @Parcelize
        data object PostAdvert : Config
    }

    @Parcelize
    private object DialogConfig : Parcelable
}