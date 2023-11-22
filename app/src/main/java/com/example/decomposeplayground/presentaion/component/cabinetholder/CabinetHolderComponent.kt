package com.example.decomposeplayground.presentaion.component.cabinetholder

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.example.decomposeplayground.presentaion.component.cabinet.CabinetComponent
import com.example.decomposeplayground.presentaion.component.settings.SettingsComponent

interface CabinetHolderComponent {

    val childStack: Value<ChildStack<*, Child>>

    fun onSettingsClicked()

    sealed interface Child {

        data class CabinetChild(val component: CabinetComponent) : Child
        data class SettingsChild(val component: SettingsComponent) : Child
    }
}