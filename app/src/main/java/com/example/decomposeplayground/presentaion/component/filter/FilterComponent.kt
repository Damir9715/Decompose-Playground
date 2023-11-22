package com.example.decomposeplayground.presentaion.component.filter

interface FilterComponent {

    val viewModel: FilterViewModel

    data class State(val sqb: Int)

    fun onFilterApplied()
    fun onIncrementSqbClicked()
}