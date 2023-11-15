package com.example.decomposeplayground.presentaion.component.messages

import com.arkivanov.decompose.ComponentContext

interface MessagesComponent {

}

class MessagesComponentImpl(
        componentContext: ComponentContext,
) : MessagesComponent, ComponentContext by componentContext {

}