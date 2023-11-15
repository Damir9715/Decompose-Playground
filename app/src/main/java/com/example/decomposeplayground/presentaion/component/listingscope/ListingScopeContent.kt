package com.example.decomposeplayground.presentaion.component.listingscope

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
import com.example.decomposeplayground.presentaion.component.filter.FilterContent

@Composable
fun ListingScopeContent(
        component: ListingScopeComponent,
        modifier: Modifier,
) {
    val childStack by component.childStack.subscribeAsState()

    Children(
            stack = childStack,
            modifier = modifier,
            animation = stackAnimation(fade()),
    ) {
        when (val child = it.instance) {
            is ListingScopeComponent.Child.AdvertList -> AdvertListContent(
                    component = child.component,
                    modifier = Modifier.fillMaxSize()
            )
            is ListingScopeComponent.Child.AdvertDetails -> AdvertDetailsContent(
                    component = child.component,
                    modifier = Modifier.fillMaxSize()
            )
            is ListingScopeComponent.Child.Filter -> FilterContent(
                    component = child.component,
                    modifier = Modifier.fillMaxSize()
            )
        }
    }
}