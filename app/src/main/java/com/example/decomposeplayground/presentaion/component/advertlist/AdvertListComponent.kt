package com.example.decomposeplayground.presentaion.component.advertlist

import com.arkivanov.decompose.value.Value

interface AdvertListComponent {

    val state: Value<State>

    fun onAdvertClicked(id: Long)
    fun onFilterClicked()

    data class State(
            val adverts: List<Advert>,
            val selectedAdvertId: Long?,
            val sqb: Int?,
    )

    data class Advert(
            val id: Long,
            val title: String,
    )
}