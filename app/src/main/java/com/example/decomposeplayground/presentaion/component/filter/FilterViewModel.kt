package com.example.decomposeplayground.presentaion.component.filter

import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.arkivanov.essenty.instancekeeper.InstanceKeeper

class FilterViewModel(
        state: FilterComponent.State,
) : InstanceKeeper.Instance {

    private val _state: MutableValue<FilterComponent.State> = MutableValue(state)
    val state: Value<FilterComponent.State> = _state

    fun onIncrementSqbClicked() {
        _state.update { _state.value.copy(sqb = _state.value.sqb + 1) }
    }
}