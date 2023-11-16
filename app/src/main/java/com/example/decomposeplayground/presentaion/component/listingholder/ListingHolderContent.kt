package com.example.decomposeplayground.presentaion.component.listingholder

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import com.example.decomposeplayground.presentaion.component.advertdetails.AdvertDetailsContent
import com.example.decomposeplayground.presentaion.component.advertlist.AdvertListContent
import com.example.decomposeplayground.presentaion.component.filter.FilterContent

@Composable
fun ListingHolderContent(
        component: ListingHolderComponent,
        modifier: Modifier,
) {
    val childStack by component.childStack.subscribeAsState()

    Children(
            stack = childStack,
            modifier = modifier,
    ) {
        when (val child = it.instance) {
            is ListingHolderComponent.Child.AdvertList -> AdvertListContent(
                    component = child.component,
                    modifier = Modifier.fillMaxSize()
            )
            is ListingHolderComponent.Child.AdvertDetails -> AdvertDetailsContent(
                    component = child.component,
                    modifier = Modifier.fillMaxSize()
            )
            is ListingHolderComponent.Child.Filter -> FilterContent(
                    component = child.component,
                    modifier = Modifier.fillMaxSize()
            )
        }
    }
}