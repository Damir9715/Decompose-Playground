package com.example.decomposeplayground.presentaion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.defaultComponentContext
import com.example.decomposeplayground.data.database.DefaultAdvertsDatabase
import com.example.decomposeplayground.domain.usecase.GetAdvertDetailsUseCase
import com.example.decomposeplayground.domain.usecase.GetAdvertListUseCase
import com.example.decomposeplayground.presentaion.component.root.RootComponentImpl
import com.example.decomposeplayground.presentaion.component.root.RootContent

class MainActivity : ComponentActivity() {

    private val database = DefaultAdvertsDatabase()
    private val getAdvertListUseCase: GetAdvertListUseCase
        get() = GetAdvertListUseCase(database)
    private val getAdvertDetailsUseCase: GetAdvertDetailsUseCase
        get() = GetAdvertDetailsUseCase(database)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val root = RootComponentImpl(
                componentContext = defaultComponentContext(),
                activity = this,
                getAdvertListUseCase = getAdvertListUseCase,
                getAdvertDetailsUseCase = getAdvertDetailsUseCase,
        )

        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    RootContent(
                            component = root,
                            modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}