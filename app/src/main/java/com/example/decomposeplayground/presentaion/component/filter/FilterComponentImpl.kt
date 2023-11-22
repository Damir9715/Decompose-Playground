package com.example.decomposeplayground.presentaion.component.filter

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.getOrCreate
import com.arkivanov.essenty.lifecycle.Lifecycle

class FilterComponentImpl(
        componentContext: ComponentContext,
        sqb: Int,
        private val setBottomNavigationVisibility: (Boolean) -> Unit,
        private val onFilterApplied: (Int) -> Unit,
) : FilterComponent, ComponentContext by componentContext {

    override val viewModel: FilterViewModel = instanceKeeper.getOrCreate { FilterViewModel(sqb) }

    init {
        lifecycle.subscribe(object : Lifecycle.Callbacks {
            override fun onStart() {
                super.onStart()
                setBottomNavigationVisibility.invoke(false)
            }
        })
    }

    override fun onFilterApplied() {
        onFilterApplied.invoke(viewModel.state.value.sqb)
    }

    override fun onIncrementSqbClicked() {
        viewModel.onIncrementSqbClicked()
    }
}