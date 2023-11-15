package com.example.decomposeplayground.presentaion.component.advertdetails

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.router.slot.dismiss
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.Lifecycle
import com.arkivanov.essenty.parcelable.Parcelable
import com.example.decomposeplayground.data.database.AdvertsDatabase
import com.example.decomposeplayground.presentaion.component.ordercall.OrderCallDialogComponent
import com.example.decomposeplayground.presentaion.component.ordercall.OrderCallDialogComponentImpl
import kotlinx.parcelize.Parcelize

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

    //dialog navigation
    private val dialogNavigation = SlotNavigation<DialogConfig>()
    private val _dialogSlot =
            childSlot<DialogConfig, OrderCallDialogComponent>(
                    source = dialogNavigation,
                    handleBackButton = true,
                    childFactory = { _, _ ->
                        OrderCallDialogComponentImpl(
                                onDismissed = dialogNavigation::dismiss,
                        )
                    }
            )
    override val dialogSlot: Value<ChildSlot<*, OrderCallDialogComponent>> = _dialogSlot

    init {
        lifecycle.subscribe(object : Lifecycle.Callbacks {
            override fun onStart() {
                super.onStart()
                hideBottomNavigation.invoke()
            }
        })
    }

    override val state: Value<AdvertDetailsComponent.State> = _state

    override fun onCloseClicked() {
        onFinished.invoke()
    }

    override fun onOrderCallClicked() {
        dialogNavigation.activate(DialogConfig)
    }

    @Parcelize
    private object DialogConfig : Parcelable
}