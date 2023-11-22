package com.example.decomposeplayground.presentaion.component.filter

import com.arkivanov.decompose.value.Value

interface FilterComponent {

    val state: Value<State>

    data class State(val sqb: Int)

    fun onFilterApplied()
}