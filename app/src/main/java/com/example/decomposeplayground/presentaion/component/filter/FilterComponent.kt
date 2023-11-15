package com.example.decomposeplayground.presentaion.component.filter

import com.arkivanov.decompose.ComponentContext

interface FilterComponent {

}

class FilterComponentImpl(
        componentContext: ComponentContext,
) : FilterComponent, ComponentContext by componentContext {

}