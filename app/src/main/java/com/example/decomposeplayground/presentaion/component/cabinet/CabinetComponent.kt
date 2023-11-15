package com.example.decomposeplayground.presentaion.component.cabinet

import com.arkivanov.decompose.ComponentContext

interface CabinetComponent {

}

class CabinetComponentImpl(
        componentContext: ComponentContext,
) : CabinetComponent, ComponentContext by componentContext {

}