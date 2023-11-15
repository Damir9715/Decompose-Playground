package com.example.decomposeplayground.presentaion.component.advertdetails

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.example.decomposeplayground.data.database.AdvertsDatabase

interface AdvertDetailsComponent {

    val state: Value<AdvertDetails>

    fun onCloseClicked()

    data class AdvertDetails(
            val title: String,
            val text: String,
    )
}

class AdvertDetailsComponentImpl(
        componentContext: ComponentContext,
        advertId: Long,
        database: AdvertsDatabase,
        private val onFinished: () -> Unit,
) : AdvertDetailsComponent, ComponentContext by componentContext {

    private val _state = MutableValue(
            database.getById(id = advertId).run {
                AdvertDetailsComponent.AdvertDetails(title, text)
            }
    )

    override val state: Value<AdvertDetailsComponent.AdvertDetails> = _state

    override fun onCloseClicked() {
        onFinished.invoke()
    }
}