package com.example.decomposeplayground.presentaion.component.listingholder

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.example.decomposeplayground.presentaion.component.advertlist.AdvertListComponent
import com.example.decomposeplayground.presentaion.component.filter.FilterComponent

interface ListingHolderComponent {

    val childStack: Value<ChildStack<*, Child>>

    fun onAdvertClicked(id: Long)
    fun onFilterClicked(sqb: Int)
    fun onFilterApplied(sqb: Int)

    sealed interface Child {

        data class AdvertListChild(val component: AdvertListComponent) : Child
        data class FilterChild(val component: FilterComponent) : Child
    }
}