package com.example.decomposeplayground.presentaion.component.listing

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
import com.example.decomposeplayground.presentaion.component.advertlist.AdvertListContent

@Composable
fun ListingContent(
        component: ListingComponent,
        modifier: Modifier,
) {
    val childStack by component.childStack.subscribeAsState()

    Box(modifier = modifier) {
        Children(
                stack = childStack,
                modifier = modifier,
                animation = stackAnimation(fade()),
        ) {
            when (val child = it.instance) {
                is ListingComponent.Child.AdvertList -> AdvertListContent(
                        component = child.component,
                        modifier = Modifier.fillMaxSize()
                )
                is ListingComponent.Child.AdvertDetails -> AdvertDetailsContent(
                        component = child.component,
                        modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}