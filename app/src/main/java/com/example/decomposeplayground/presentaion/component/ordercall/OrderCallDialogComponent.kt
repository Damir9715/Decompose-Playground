package com.example.decomposeplayground.presentaion.component.ordercall

interface OrderCallDialogComponent {

    fun onDismissed()
}

class OrderCallDialogComponentImpl(
        private val onDismissed: () -> Unit,
) : OrderCallDialogComponent {

    override fun onDismissed() {
        onDismissed.invoke()
    }
}