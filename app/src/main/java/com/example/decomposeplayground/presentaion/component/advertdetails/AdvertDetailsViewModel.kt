package com.example.decomposeplayground.presentaion.component.advertdetails

import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.example.decomposeplayground.data.database.AdvertsDatabase

class AdvertDetailsViewModel(
        advertId: Long,
        database: AdvertsDatabase,
) : InstanceKeeper.Instance {

    private val _state = MutableValue(
            database.getById(id = advertId).run {
                State(
                        AdvertDetails(title, text)
                )
            }
    )

    val state: Value<State> = _state

    data class State(val advertDetails: AdvertDetails)

    data class AdvertDetails(
            val title: String,
            val text: String,
    )
}