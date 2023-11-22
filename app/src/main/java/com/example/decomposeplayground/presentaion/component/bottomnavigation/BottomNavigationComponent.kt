package com.example.decomposeplayground.presentaion.component.bottomnavigation

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.example.decomposeplayground.presentaion.component.cabinetholder.CabinetHolderComponent
import com.example.decomposeplayground.presentaion.component.favorites.FavoritesComponent
import com.example.decomposeplayground.presentaion.component.listingholder.ListingHolderComponent
import com.example.decomposeplayground.presentaion.component.messages.MessagesComponent

interface BottomNavigationComponent {

    val state: Value<State>

    val childStack: Value<ChildStack<*, Child>>

    fun onAdvertListTabClicked()
    fun onFavoritesTabClicked()
    fun onPostAdvertTabClicked()
    fun onMessagesTabClicked()
    fun onCabinetTabClicked()

    fun setBottomNavigationVisibility(isVisible: Boolean)

    data class State(val isBottomNavigationVisible: Boolean)

    sealed interface Child {

        data class AdvertListChild(val component: ListingHolderComponent) : Child
        data class FavoritesChild(val component: FavoritesComponent) : Child
        data class MessagesChild(val component: MessagesComponent) : Child
        data class CabinetChild(val component: CabinetHolderComponent) : Child
    }
}