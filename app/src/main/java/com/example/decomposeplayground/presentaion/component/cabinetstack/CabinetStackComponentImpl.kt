package com.example.decomposeplayground.presentaion.component.cabinetstack

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.example.decomposeplayground.presentaion.component.cabinet.CabinetComponentImpl
import com.example.decomposeplayground.presentaion.component.settings.SettingsComponentImpl
import kotlinx.parcelize.Parcelize

class CabinetStackComponentImpl(
        componentContext: ComponentContext,
) : CabinetStackComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()
    override val childStack: Value<ChildStack<*, CabinetStackComponent.Child>> =
            childStack(
                    source = navigation,
                    initialConfiguration = Config.Cabinet,
                    handleBackButton = true,
                    childFactory = ::child,
            )

    private fun child(config: Config, componentContext: ComponentContext): CabinetStackComponent.Child =
            when (config) {
                is Config.Cabinet -> CabinetStackComponent.Child.CabinetChild(
                        component = CabinetComponentImpl(
                                componentContext = componentContext,
                                onSettingsClicked = ::onSettingsClicked,
                        )
                )
                is Config.Settings -> CabinetStackComponent.Child.SettingsChild(
                        component = SettingsComponentImpl(
                                componentContext = componentContext,
                        )
                )
            }

    override fun onSettingsClicked() {
        navigation.push(Config.Settings)
    }

    private sealed interface Config : Parcelable {

        @Parcelize
        data object Cabinet : Config

        @Parcelize
        data object Settings : Config
    }
}