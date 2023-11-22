package com.example.decomposeplayground.presentaion.component.filter

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

interface FilterComponent {

    val viewModel: FilterViewModel

    @Parcelize
    data class State(val sqb: Int) : Parcelable

    fun onFilterApplied()
    fun onIncrementSqbClicked()
}