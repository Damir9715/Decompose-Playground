package com.example.decomposeplayground.presentaion.component.filter

import com.arkivanov.decompose.ComponentContext

interface FilterComponent {

    fun onApplyClicked()
}

class FilterComponentImpl(
        componentContext: ComponentContext,
//        private val onApplyClicked: () -> Unit,
) : FilterComponent, ComponentContext by componentContext {

    override fun onApplyClicked() {
//        onApplyClicked.invoke()
    }
}