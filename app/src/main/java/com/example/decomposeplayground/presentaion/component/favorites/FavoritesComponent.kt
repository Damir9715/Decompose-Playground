package com.example.decomposeplayground.presentaion.component.favorites

import com.arkivanov.decompose.ComponentContext

interface FavoritesComponent {

}

class FavoritesComponentImpl(
        componentContext: ComponentContext,
) : FavoritesComponent, ComponentContext by componentContext {

}