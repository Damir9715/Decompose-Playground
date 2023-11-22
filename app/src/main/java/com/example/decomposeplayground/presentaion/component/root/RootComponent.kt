package com.example.decomposeplayground.presentaion.component.root

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.example.decomposeplayground.presentaion.component.advertdetails.AdvertDetailsComponent
import com.example.decomposeplayground.presentaion.component.bottomnavigation.BottomNavigationComponent
import com.example.decomposeplayground.presentaion.component.postadvert.PostAdvertComponent

interface RootComponent {

    val state: Value<State>

    val childStack: Value<ChildStack<*, Child>>

    fun onPostAdvertTabClicked()
    fun onToastShown()
    fun setBackCallback(isEnabled: Boolean)
    fun onAdvertClicked(id: Long)
    fun onAdvertDetailsCloseClicked()

    data class State(val toast: String? = null)

    sealed interface Child {

        data class BottomNavigationChild(val component: BottomNavigationComponent) : Child
        data class PostAdvertChild(val component: PostAdvertComponent) : Child
        data class AdvertDetailsChild(val component: AdvertDetailsComponent) : Child
    }
}