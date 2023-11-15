package com.example.decomposeplayground.presentaion.component.bottomnavigation

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
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import com.example.decomposeplayground.presentaion.component.cabinetholder.CabinetHolderContent
import com.example.decomposeplayground.presentaion.component.favorites.FavoritesContent
import com.example.decomposeplayground.presentaion.component.listingholder.ListingHolderContent
import com.example.decomposeplayground.presentaion.component.messages.MessagesContent
import com.example.decomposeplayground.presentaion.component.postadvert.PostAdvertContent

@Composable
fun BottomNavigationContent(
        component: BottomNavigationComponent,
        modifier: Modifier = Modifier,
) {
    val childStack by component.childStack.subscribeAsState()
    val activeComponent = childStack.active.instance
    val state by component.state.subscribeAsState()

    Column(modifier = modifier) {
        Children(
                stack = childStack,
                modifier = Modifier.weight(weight = 1F),
        ) {
            when (val child = it.instance) {
                is BottomNavigationComponent.Child.AdvertListChild -> ListingHolderContent(
                        component = child.component,
                        modifier = Modifier.fillMaxSize()
                )
                is BottomNavigationComponent.Child.FavoritesChild -> FavoritesContent(
                        component = child.component,
                        modifier = Modifier.fillMaxSize()
                )
                is BottomNavigationComponent.Child.PostAdvertChild -> PostAdvertContent(
                        component = child.component,
                        modifier = Modifier.fillMaxSize()
                )
                is BottomNavigationComponent.Child.MessagesChild -> MessagesContent(
                        component = child.component,
                        modifier = Modifier.fillMaxSize()
                )
                is BottomNavigationComponent.Child.CabinetChild -> CabinetHolderContent(
                        component = child.component,
                        modifier = Modifier.fillMaxSize()
                )
            }
        }

        if (state.isBottomNavigationVisible) {
            BottomNavigation(modifier = Modifier.fillMaxWidth()) {
                BottomNavigationItem(
                        selected = activeComponent is BottomNavigationComponent.Child.AdvertListChild,
                        onClick = component::onAdvertListTabClicked,
                        icon = { Icon(Icons.Default.Home, "Home") },
                )
                BottomNavigationItem(
                        selected = activeComponent is BottomNavigationComponent.Child.FavoritesChild,
                        onClick = component::onFavoritesTabClicked,
                        icon = { Icon(Icons.Filled.Favorite, "Favorites") },
                )
                BottomNavigationItem(
                        selected = false,
                        onClick = component::onPostAdvertTabClicked,
                        icon = { Icon(Icons.Default.AddCircle, "PostAdvert") },
                )
                BottomNavigationItem(
                        selected = activeComponent is BottomNavigationComponent.Child.MessagesChild,
                        onClick = component::onMessagesTabClicked,
                        icon = { Icon(Icons.Default.MailOutline, "Messages") },
                )
                BottomNavigationItem(
                        selected = activeComponent is BottomNavigationComponent.Child.CabinetChild,
                        onClick = component::onCabinetTabClicked,
                        icon = { Icon(Icons.Default.AccountCircle, "Cabinet") },
                )
            }
        }
    }
}