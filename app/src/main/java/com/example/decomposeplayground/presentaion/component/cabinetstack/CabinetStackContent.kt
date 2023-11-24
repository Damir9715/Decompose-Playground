package com.example.decomposeplayground.presentaion.component.cabinetstack

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import com.example.decomposeplayground.presentaion.component.cabinet.CabinetContent
import com.example.decomposeplayground.presentaion.component.settings.SettingsContent

@Composable
fun CabinetStackContent(
        component: CabinetStackComponent,
        modifier: Modifier,
) {
    val childStack by component.childStack.subscribeAsState()

    Children(
            stack = childStack,
            modifier = modifier,
    ) {
        when (val child = it.instance) {
            is CabinetStackComponent.Child.CabinetChild -> CabinetContent(
                    component = child.component,
                    modifier = Modifier.fillMaxSize()
            )
            is CabinetStackComponent.Child.SettingsChild -> SettingsContent(
                    component = child.component,
                    modifier = Modifier.fillMaxSize()
            )
        }
    }
}