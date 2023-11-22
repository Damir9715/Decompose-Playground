package com.example.decomposeplayground.presentaion.component.advertdetails

import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.example.decomposeplayground.domain.model.AdvertDetails
import com.example.decomposeplayground.domain.usecase.GetAdvertDetailsUseCase

class AdvertDetailsViewModel(
        advertId: Long,
        getAdvertDetailsUseCase: GetAdvertDetailsUseCase,
) : InstanceKeeper.Instance {

    private val _state = MutableValue(
            State(
                    getAdvertDetailsUseCase.execute(advertId)
            )
    )
    val state: Value<State> = _state

    data class State(val advertDetails: AdvertDetails)
}