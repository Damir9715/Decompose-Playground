package com.example.decomposeplayground.presentaion.component.host

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import com.example.decomposeplayground.presentaion.component.bottomnavigation.BottomNavigationContent
import com.example.decomposeplayground.presentaion.component.postadvert.PostAdvertContent


@Composable
fun HostContent(
        component: HostComponent,
        modifier: Modifier = Modifier,
) {
    val childStack by component.childStack.subscribeAsState()

    Children(
            stack = childStack,
            modifier = modifier,
    ) {
        when (val child = it.instance) {
            is HostComponent.Child.MainTabsChild -> BottomNavigationContent(
                    component = child.component,
                    modifier = Modifier.fillMaxSize()
            )
            is HostComponent.Child.PostAdvertChild -> PostAdvertContent(
                    component = child.component,
                    modifier = Modifier.fillMaxSize()
            )
        }
    }
}