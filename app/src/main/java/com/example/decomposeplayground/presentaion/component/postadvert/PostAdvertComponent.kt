package com.example.decomposeplayground.presentaion.component.postadvert

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.Lifecycle

interface PostAdvertComponent {

}

class PostAdvertComponentImpl(
        componentContext: ComponentContext,
        private val showBottomNavigation: () -> Unit,
        private val hideBottomNavigation: () -> Unit,
) : PostAdvertComponent, ComponentContext by componentContext {

    init {
        lifecycle.subscribe(object : Lifecycle.Callbacks {
            override fun onStart() {
                super.onCreate()
                hideBottomNavigation.invoke()
            }

            override fun onStop() {
                super.onStop()
                showBottomNavigation.invoke()
            }
        })
    }
}