package com.example.decomposeplayground.presentaion.component.advertlist

import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.example.decomposeplayground.data.database.AdvertsDatabase

class AdvertListViewModel(
        database: AdvertsDatabase,
        sqb: Int,
) : InstanceKeeper.Instance {

    private val _state: MutableValue<State> = MutableValue(
            State(
                    adverts = database.getAll().map {
                        Advert(it.id, it.title)
                    },
                    selectedAdvertId = null,
                    sqb = sqb,
            )
    )
    val state: Value<State> = _state

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