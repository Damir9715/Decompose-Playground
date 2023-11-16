package com.example.decomposeplayground.presentaion.component.host

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.example.decomposeplayground.presentaion.component.bottomnavigation.BottomNavigationComponent
import com.example.decomposeplayground.presentaion.component.bottomnavigation.BottomNavigationComponentImpl
import com.example.decomposeplayground.presentaion.component.postadvert.PostAdvertComponent
import com.example.decomposeplayground.presentaion.component.postadvert.PostAdvertComponentImpl
import kotlinx.parcelize.Parcelize

interface HostComponent {

    val childStack: Value<ChildStack<*, Child>>

    fun onPostAdvertTabClicked()

    sealed interface Child {
        data class MainTabsChild(val component: BottomNavigationComponent) : Child
        data class PostAdvertChild(val component: PostAdvertComponent) : Child
    }
}

class HostComponentImpl(
        componentContext: ComponentContext,
) : HostComponent, ComponentContext by componentContext {

    //stack navigation
    private val navigation = StackNavigation<Config>()
    override val childStack: Value<ChildStack<*, HostComponent.Child>> =
            childStack(
                    source = navigation,
                    initialConfiguration = Config.MainTabs,
                    handleBackButton = true,
                    childFactory = ::child,
            )

    private fun child(config: Config, componentContext: ComponentContext): HostComponent.Child =
            when (config) {
                is Config.MainTabs -> HostComponent.Child.MainTabsChild(
                        component = BottomNavigationComponentImpl(
                                componentContext = componentContext,
                                onPostAdvertTabClicked = ::onPostAdvertTabClicked,
                        )
                )
                is Config.PostAdvert -> HostComponent.Child.PostAdvertChild(
                        component = PostAdvertComponentImpl(
                                componentContext = componentContext,
                        )
                )
            }

    override fun onPostAdvertTabClicked() {
        navigation.push(Config.PostAdvert)
    }

    private sealed interface Config : Parcelable {

        @Parcelize
        data object MainTabs : Config

        @Parcelize
        data object PostAdvert : Config
    }
}