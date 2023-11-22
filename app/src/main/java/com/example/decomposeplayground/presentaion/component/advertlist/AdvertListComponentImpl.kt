package com.example.decomposeplayground.presentaion.component.advertlist

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.getOrCreate
import com.arkivanov.essenty.lifecycle.Lifecycle
import com.example.decomposeplayground.domain.usecase.GetAdvertListUseCase

class AdvertListComponentImpl(
        componentContext: ComponentContext,
        getAdvertListUseCase: GetAdvertListUseCase,
        private val sqb: Int,
        private val onAdvertClicked: (Long) -> Unit,
        private val onFilterClicked: (Int) -> Unit,
        private val setBottomNavigationVisibility: (Boolean) -> Unit,
) : AdvertListComponent, ComponentContext by componentContext {

    override val viewModel = instanceKeeper.getOrCreate { AdvertListViewModel(sqb, getAdvertListUseCase) }

    init {
        lifecycle.subscribe(object : Lifecycle.Callbacks {
            override fun onStart() {
                super.onCreate()
                setBottomNavigationVisibility.invoke(true)
            }
        })
    }

    override fun onAdvertClicked(id: Long) {
        onAdvertClicked.invoke(id)
    }

    override fun onFilterClicked() {
        onFilterClicked.invoke(sqb)
    }
}