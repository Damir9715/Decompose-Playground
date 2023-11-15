package com.example.decomposeplayground.presentaion.component.maintabs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import com.example.decomposeplayground.presentaion.component.listing.ListingContent
import com.example.decomposeplayground.presentaion.component.cabinet.CabinetContent
import com.example.decomposeplayground.presentaion.component.favorites.FavoritesContent
import com.example.decomposeplayground.presentaion.component.messages.MessagesContent

@Composable
fun MainTabsContent(
        component: MainTabsComponent,
        modifier: Modifier = Modifier,
) {
    val childStack by component.childStack.subscribeAsState()
    val activeComponent = childStack.active.instance

    Column(modifier = modifier) {
        Children(
                stack = childStack,
                modifier = Modifier.weight(weight = 1F),
                animation = stackAnimation(fade()),
        ) {
            when (val child = it.instance) {
                is MainTabsComponent.Child.AdvertListChild -> ListingContent(
                        component = child.component,
                        modifier = Modifier.fillMaxSize()
                )
                is MainTabsComponent.Child.FavoritesChild -> FavoritesContent(
                        component = child.component,
                        modifier = Modifier.fillMaxSize()
                )
                is MainTabsComponent.Child.MessagesChild -> MessagesContent(
                        component = child.component,
                        modifier = Modifier.fillMaxSize()
                )
                is MainTabsComponent.Child.CabinetChild -> CabinetContent(
                        component = child.component,
                        modifier = Modifier.fillMaxSize()
                )
            }
        }

        BottomNavigation(modifier = Modifier.fillMaxWidth()) {
            BottomNavigationItem(
                    selected = activeComponent is MainTabsComponent.Child.AdvertListChild,
                    onClick = component::onAdvertListTabClicked,
                    icon = { Icon(Icons.Default.Home, "Home") },
            )
            BottomNavigationItem(
                    selected = activeComponent is MainTabsComponent.Child.FavoritesChild,
                    onClick = component::onFavoritesTabClicked,
                    icon = { Icon(Icons.Filled.Favorite, "Favorites") },
            )
            BottomNavigationItem(
                    selected = false,
                    onClick = component::onPostAdvertTabClicked,
                    icon = { Icon(Icons.Default.AddCircle, "PostAdvert") },
            )
            BottomNavigationItem(
                    selected = activeComponent is MainTabsComponent.Child.MessagesChild,
                    onClick = component::onMessagesTabClicked,
                    icon = { Icon(Icons.Default.MailOutline, "Messages") },
            )
            BottomNavigationItem(
                    selected = activeComponent is MainTabsComponent.Child.CabinetChild,
                    onClick = component::onCabinetTabClicked,
                    icon = { Icon(Icons.Default.AccountCircle, "Cabinet") },
            )
        }
    }
}