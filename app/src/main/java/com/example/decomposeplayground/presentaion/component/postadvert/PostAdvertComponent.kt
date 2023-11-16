package com.example.decomposeplayground.presentaion.component.postadvert

import com.arkivanov.decompose.ComponentContext

interface PostAdvertComponent {

}

class PostAdvertComponentImpl(
        componentContext: ComponentContext,
) : PostAdvertComponent, ComponentContext by componentContext {

}