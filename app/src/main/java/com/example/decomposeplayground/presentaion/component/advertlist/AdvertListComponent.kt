package com.example.decomposeplayground.presentaion.component.advertlist

interface AdvertListComponent {

    val viewModel: AdvertListViewModel

    fun onAdvertClicked(id: Long)
    fun onFilterClicked()
}