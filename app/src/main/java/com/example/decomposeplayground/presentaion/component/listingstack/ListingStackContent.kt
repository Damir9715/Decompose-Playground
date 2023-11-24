package com.example.decomposeplayground.presentaion.component.listingstack

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import com.example.decomposeplayground.presentaion.component.advertlist.AdvertListContent
import com.example.decomposeplayground.presentaion.component.filter.FilterContent

@Composable
fun ListingStackContent(
        component: ListingStackComponent,
        modifier: Modifier,
) {
    val childStack by component.childStack.subscribeAsState()

    Children(
            stack = childStack,
            modifier = modifier,
    ) {
        when (val child = it.instance) {
            is ListingStackComponent.Child.AdvertListChild -> AdvertListContent(
                    component = child.component,
                    modifier = Modifier.fillMaxSize()
            )
            is ListingStackComponent.Child.FilterChild -> FilterContent(
                    component = child.component,
                    modifier = Modifier.fillMaxSize()
            )
        }
    }
}