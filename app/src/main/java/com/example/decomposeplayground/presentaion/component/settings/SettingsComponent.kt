package com.example.decomposeplayground.presentaion.component.settings

import com.arkivanov.decompose.ComponentContext

interface SettingsComponent {

}

class SettingsComponentImpl(
        componentContext: ComponentContext,
) : SettingsComponent, ComponentContext by componentContext {

}