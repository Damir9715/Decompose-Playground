package com.example.decomposeplayground.presentaion.component.filter

import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.arkivanov.essenty.instancekeeper.InstanceKeeper

class FilterViewModel(
        sqb: Int,
) : InstanceKeeper.Instance {

    private val _state: MutableValue<FilterComponent.State> = MutableValue(
            FilterComponent.State(sqb)
    )
    val state: Value<FilterComponent.State> = _state

    fun onIncrementSqbClicked() {
        _state.update { _state.value.copy(sqb = _state.value.sqb + 1) }
    }
}