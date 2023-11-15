package com.example.decomposeplayground.presentaion.component.filter

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.Lifecycle

interface FilterComponent {

    fun onApplyClicked()
}

class FilterComponentImpl(
        componentContext: ComponentContext,
        private val hideBottomNavigation: () -> Unit,
//        private val onApplyClicked: () -> Unit,
) : FilterComponent, ComponentContext by componentContext {

    init {
        lifecycle.subscribe(object : Lifecycle.Callbacks {
            override fun onStart() {
                super.onCreate()
                hideBottomNavigation.invoke()
            }
        })
    }

    override fun onApplyClicked() {
//        onApplyClicked.invoke()
    }
}