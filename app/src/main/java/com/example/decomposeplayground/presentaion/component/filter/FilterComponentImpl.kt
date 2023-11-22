package com.example.decomposeplayground.presentaion.component.filter

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.Lifecycle

class FilterComponentImpl(
        componentContext: ComponentContext,
        private val sqb: Int,
        private val setBottomNavigationVisibility: (Boolean) -> Unit,
        private val onFilterApplied: (Int) -> Unit,
) : FilterComponent, ComponentContext by componentContext {

    private val _state: MutableValue<FilterComponent.State> = MutableValue(
            FilterComponent.State(sqb)
    )
    override val state: Value<FilterComponent.State> = _state

    init {
        lifecycle.subscribe(object : Lifecycle.Callbacks {
            override fun onStart() {
                super.onStart()
                setBottomNavigationVisibility.invoke(false)
            }
        })
    }

    override fun onFilterApplied() {
        onFilterApplied.invoke(sqb + 1)
    }
}