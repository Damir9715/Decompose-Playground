package com.example.decomposeplayground.presentaion.component.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.example.decomposeplayground.data.database.DefaultAdvertsDatabase
import com.example.decomposeplayground.presentaion.component.advertdetails.AdvertDetailsComponent
import com.example.decomposeplayground.presentaion.component.advertdetails.AdvertDetailsComponentImpl
import com.example.decomposeplayground.presentaion.component.maintabs.MainTabsComponent
import com.example.decomposeplayground.presentaion.component.maintabs.MainTabsComponentImpl
import com.example.decomposeplayground.presentaion.component.postadvert.PostAdvertComponent
import com.example.decomposeplayground.presentaion.component.postadvert.PostAdvertComponentImpl
import kotlinx.parcelize.Parcelize

interface RootComponent {

    val childStack: Value<ChildStack<*, Child>>

    fun onPostAdvertTabClicked()

    fun onAdvertClicked(id: Long)

    fun onAdvertDetailsCloseClicked()

    sealed interface Child {

        data class MainTabsChild(val component: MainTabsComponent) : Child
        data class PostAdvertChild(val component: PostAdvertComponent) : Child
        data class AdvertDetails(val component: AdvertDetailsComponent) : Child
    }
}

class RootComponentImpl(
        componentContext: ComponentContext,
) : RootComponent, ComponentContext by componentContext {

    private val database = DefaultAdvertsDatabase()

    private val navigation = StackNavigation<Config>()

    override val childStack: Value<ChildStack<*, RootComponent.Child>> =
            childStack(
                    source = navigation,
                    initialConfiguration = Config.MainTabs,
                    handleBackButton = true,
                    childFactory = ::child,
            )

    override fun onPostAdvertTabClicked() {
        navigation.push(Config.PostAdvert)
    }

    override fun onAdvertClicked(id: Long) {
        navigation.push(Config.AdvertDetails(id))
    }

    override fun onAdvertDetailsCloseClicked() {
        navigation.pop()
    }

    private fun child(config: Config, componentContext: ComponentContext): RootComponent.Child =
            when (config) {
                is Config.MainTabs -> RootComponent.Child.MainTabsChild(
                        component = MainTabsComponentImpl(
                                componentContext = componentContext,
                                database = database,
                                onPostAdvertTabClicked = ::onPostAdvertTabClicked,
                                onAdvertClicked = ::onAdvertClicked,
                        )
                )
                is Config.PostAdvert -> RootComponent.Child.PostAdvertChild(
                        component = PostAdvertComponentImpl(
                                componentContext = componentContext
                        )
                )
                is Config.AdvertDetails -> RootComponent.Child.AdvertDetails(
                        component = AdvertDetailsComponentImpl(
                                componentContext = componentContext,
                                database = database,
                                advertId = config.id,
                                onFinished = ::onAdvertDetailsCloseClicked,
                        )
                )
            }

    private sealed interface Config : Parcelable {

        @Parcelize
        data object MainTabs : Config

        @Parcelize
        data object PostAdvert : Config

        @Parcelize
        data class AdvertDetails(val id: Long) : Config
    }
}