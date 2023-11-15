package com.example.decomposeplayground.presentaion.component.cabinetscope

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import com.example.decomposeplayground.presentaion.component.cabinet.CabinetContent
import com.example.decomposeplayground.presentaion.component.settings.SettingsContent

@Composable
fun CabinetScopeContent(
        component: CabinetScopeComponent,
        modifier: Modifier,
) {
    val childStack by component.childStack.subscribeAsState()

    Children(
            stack = childStack,
            modifier = modifier,
            animation = stackAnimation(fade()),
    ) {
        when (val child = it.instance) {
            is CabinetScopeComponent.Child.CabinetChild -> CabinetContent(
                    component = child.component,
                    modifier = Modifier.fillMaxSize()
            )
            is CabinetScopeComponent.Child.SettingsChild -> SettingsContent(
                    component = child.component,
                    modifier = Modifier.fillMaxSize()
            )
        }
    }
}