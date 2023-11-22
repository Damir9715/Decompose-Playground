package com.example.decomposeplayground.presentaion.component.root

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import com.example.decomposeplayground.presentaion.component.advertdetails.AdvertDetailsContent
import com.example.decomposeplayground.presentaion.component.bottomnavigation.BottomNavigationContent
import com.example.decomposeplayground.presentaion.component.postadvert.PostAdvertContent

@Composable
fun RootContent(
        component: RootComponent,
        modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val state by component.state.subscribeAsState()
    val childStack by component.childStack.subscribeAsState()

    Children(
            stack = childStack,
            modifier = modifier,
    ) {
        when (val child = it.instance) {
            is RootComponent.Child.BottomNavigationChild -> BottomNavigationContent(
                    component = child.component,
                    modifier = Modifier.fillMaxSize()
            )
            is RootComponent.Child.PostAdvertChild -> PostAdvertContent(
                    component = child.component,
                    modifier = Modifier.fillMaxSize()
            )
            is RootComponent.Child.AdvertDetailsChild -> AdvertDetailsContent(
                    component = child.component,
                    modifier = Modifier.fillMaxSize()
            )
        }
    }

    LaunchedEffect(key1 = state.toast) {
        state.toast?.let { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            component.onToastShown()
        }
    }
}