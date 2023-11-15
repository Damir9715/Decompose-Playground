package com.example.decomposeplayground.presentaion.component.cabinetscope

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.example.decomposeplayground.presentaion.component.cabinet.CabinetComponent
import com.example.decomposeplayground.presentaion.component.cabinet.CabinetComponentImpl
import com.example.decomposeplayground.presentaion.component.settings.SettingsComponent
import com.example.decomposeplayground.presentaion.component.settings.SettingsComponentImpl
import kotlinx.parcelize.Parcelize

interface CabinetScopeComponent {

    val childStack: Value<ChildStack<*, Child>>

    fun onSettingsClicked()

    sealed interface Child {

        data class CabinetChild(val component: CabinetComponent) : Child
        data class SettingsChild(val component: SettingsComponent) : Child
    }
}

class CabinetScopeComponentImpl(
        componentContext: ComponentContext,
) : CabinetScopeComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()

    override val childStack: Value<ChildStack<*, CabinetScopeComponent.Child>> =
            childStack(
                    source = navigation,
                    initialConfiguration = Config.Cabinet,
                    handleBackButton = true,
                    childFactory = ::child,
            )

    override fun onSettingsClicked() {
        navigation.push(Config.Settings)
    }

    private fun child(config: Config, componentContext: ComponentContext): CabinetScopeComponent.Child =
            when (config) {
                is Config.Cabinet -> CabinetScopeComponent.Child.CabinetChild(
                        component = CabinetComponentImpl(
                                componentContext = componentContext,
                                onSettingsClicked = ::onSettingsClicked,
                        )
                )
                is Config.Settings -> CabinetScopeComponent.Child.SettingsChild(
                        component = SettingsComponentImpl(
                                componentContext = componentContext,
                        )
                )
            }

    private sealed interface Config : Parcelable {

        @Parcelize
        data object Cabinet : Config

        @Parcelize
        data object Settings : Config
    }
}