package com.example.decomposeplayground.presentaion.component.advertlist

import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.example.decomposeplayground.domain.model.Advert
import com.example.decomposeplayground.domain.usecase.GetAdvertListUseCase

class AdvertListViewModel(
        sqb: Int,
        getAdvertListUseCase: GetAdvertListUseCase,
) : InstanceKeeper.Instance {

    private val _state: MutableValue<State> = MutableValue(
            State(
                    adverts = getAdvertListUseCase.execute(),
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
}