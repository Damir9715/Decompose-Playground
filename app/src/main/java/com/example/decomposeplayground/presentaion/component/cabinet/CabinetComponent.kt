package com.example.decomposeplayground.presentaion.component.cabinet

import com.arkivanov.decompose.ComponentContext

interface CabinetComponent {

    fun onSettingsClicked()
}

class CabinetComponentImpl(
        componentContext: ComponentContext,
        private val onSettingsClicked: () -> Unit,
) : CabinetComponent, ComponentContext by componentContext {

    override fun onSettingsClicked() {
        onSettingsClicked.invoke()
    }
}