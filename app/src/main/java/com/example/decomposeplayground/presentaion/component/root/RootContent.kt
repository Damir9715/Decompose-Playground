package com.example.decomposeplayground.presentaion.component.root

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import com.example.decomposeplayground.presentaion.component.advertdetails.AdvertDetailsContent
import com.example.decomposeplayground.presentaion.component.filter.FilterContent
import com.example.decomposeplayground.presentaion.component.maintabs.MainTabsContent
import com.example.decomposeplayground.presentaion.component.postadvert.PostAdvertContent


@Composable
fun RootContent(
        component: RootComponent,
        modifier: Modifier = Modifier,
) {
    val childStack by component.childStack.subscribeAsState()

    Box(modifier = modifier) {
        Children(
                stack = childStack,
                modifier = modifier,
                animation = stackAnimation(fade()),
        ) {
            when (val child = it.instance) {
                is RootComponent.Child.MainTabsChild -> MainTabsContent(
                        component = child.component,
                        modifier = Modifier.fillMaxSize()
                )
                is RootComponent.Child.PostAdvertChild -> PostAdvertContent(
                        component = child.component,
                        modifier = Modifier.fillMaxSize()
                )
                is RootComponent.Child.AdvertDetails -> AdvertDetailsContent(
                        component = child.component,
                        modifier = Modifier.fillMaxSize()
                )
                is RootComponent.Child.Filter -> FilterContent(
                        component = child.component,
                        modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}