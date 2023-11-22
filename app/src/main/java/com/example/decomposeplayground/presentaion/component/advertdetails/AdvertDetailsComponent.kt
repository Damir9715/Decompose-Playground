package com.example.decomposeplayground.presentaion.component.advertdetails

import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.value.Value
import com.example.decomposeplayground.presentaion.component.ordercall.OrderCallDialogComponent

interface AdvertDetailsComponent {

    val state: Value<State>

    val dialogSlot: Value<ChildSlot<*, OrderCallDialogComponent>>

    fun onCloseClicked()
    fun onOrderCallClicked()

    data class State(val advertDetails: AdvertDetails)

    data class AdvertDetails(
            val title: String,
            val text: String,
    )
}