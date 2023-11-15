package com.example.decomposeplayground.presentaion.component.advertlist

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.example.decomposeplayground.data.database.AdvertsDatabase

interface AdvertListComponent {

    val state: Value<State>

    fun onAdvertClicked(id: Long)

    data class State(
            val adverts: List<Advert>,
            val selectedAdvertId: Long?,
    )

    data class Advert(
            val id: Long,
            val title: String,
    )
}

class AdvertListComponentImpl(
        componentContext: ComponentContext,
        database: AdvertsDatabase,
        private val onAdvertClicked: (Long) -> Unit,
) : AdvertListComponent, ComponentContext by componentContext {

    private val _state = MutableValue(
            AdvertListComponent.State(
                    adverts = database.getAll().map {
                        AdvertListComponent.Advert(it.id, it.title)
                    },
                    selectedAdvertId = null,
            )
    )

    override val state: Value<AdvertListComponent.State> = _state

    override fun onAdvertClicked(id: Long) {
        onAdvertClicked.invoke(id)
    }
}