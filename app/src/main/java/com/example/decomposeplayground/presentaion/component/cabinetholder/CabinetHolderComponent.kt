package com.example.decomposeplayground.presentaion.component.cabinetholder

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

interface CabinetHolderComponent {

    val childStack: Value<ChildStack<*, Child>>

    fun onSettingsClicked()

    sealed interface Child {

        data class CabinetChild(val component: CabinetComponent) : Child
        data class SettingsChild(val component: SettingsComponent) : Child
    }
}

class CabinetHolderComponentImpl(
        componentContext: ComponentContext,
) : CabinetHolderComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()

    override val childStack: Value<ChildStack<*, CabinetHolderComponent.Child>> =
            childStack(
                    source = navigation,
                    initialConfiguration = Config.Cabinet,
                    handleBackButton = true,
                    childFactory = ::child,
            )

    override fun onSettingsClicked() {
        navigation.push(Config.Settings)
    }

    private fun child(config: Config, componentContext: ComponentContext): CabinetHolderComponent.Child =
            when (config) {
                is Config.Cabinet -> CabinetHolderComponent.Child.CabinetChild(
                        component = CabinetComponentImpl(
                                componentContext = componentContext,
                                onSettingsClicked = ::onSettingsClicked,
                        )
                )
                is Config.Settings -> CabinetHolderComponent.Child.SettingsChild(
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