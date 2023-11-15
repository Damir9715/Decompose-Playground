package com.example.decomposeplayground.presentaion.component.advertdetails

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.Lifecycle
import com.example.decomposeplayground.data.database.AdvertsDatabase

interface AdvertDetailsComponent {

    val state: Value<State>

    fun onCloseClicked()

    data class State(val advertDetails: AdvertDetails)

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
        private val hideBottomNavigation: () -> Unit,
) : AdvertDetailsComponent, ComponentContext by componentContext {

    private val _state = MutableValue(
            database.getById(id = advertId).run {
                AdvertDetailsComponent.State(
                        AdvertDetailsComponent.AdvertDetails(title, text)
                )
            }
    )

    init {
        lifecycle.subscribe(object : Lifecycle.Callbacks {
            override fun onStart() {
                super.onCreate()
                hideBottomNavigation.invoke()
            }
        })
    }

    override val state: Value<AdvertDetailsComponent.State> = _state

    override fun onCloseClicked() {
        onFinished.invoke()
    }
}