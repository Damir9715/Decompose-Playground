package com.example.decomposeplayground.presentaion.component.advertdetails

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.router.slot.dismiss
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.instancekeeper.getOrCreate
import com.arkivanov.essenty.parcelable.Parcelable
import com.example.decomposeplayground.domain.usecase.GetAdvertDetailsUseCase
import com.example.decomposeplayground.presentaion.component.ordercall.OrderCallDialogComponent
import com.example.decomposeplayground.presentaion.component.ordercall.OrderCallDialogComponentImpl
import kotlinx.parcelize.Parcelize

class AdvertDetailsComponentImpl(
        componentContext: ComponentContext,
        advertId: Long,
        getAdvertDetailsUseCase: GetAdvertDetailsUseCase,
        private val onFinished: () -> Unit,
) : AdvertDetailsComponent, ComponentContext by componentContext {

    override val viewModel = instanceKeeper.getOrCreate { AdvertDetailsViewModel(advertId, getAdvertDetailsUseCase) }

    //dialog navigation
    private val dialogNavigation = SlotNavigation<DialogConfig>()
    private val _dialogSlot = childSlot<DialogConfig, OrderCallDialogComponent>(
            source = dialogNavigation,
            handleBackButton = true,
            childFactory = { _, _ ->
                OrderCallDialogComponentImpl(
                        onDismissed = dialogNavigation::dismiss,
                )
            }
    )
    override val dialogSlot: Value<ChildSlot<*, OrderCallDialogComponent>> = _dialogSlot

    override fun onCloseClicked() {
        onFinished.invoke()
    }

    override fun onOrderCallClicked() {
        dialogNavigation.activate(DialogConfig)
    }

    @Parcelize
    private object DialogConfig : Parcelable
}