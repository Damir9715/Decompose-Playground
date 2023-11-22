package com.example.decomposeplayground.domain.usecase

import com.example.decomposeplayground.domain.model.AdvertDetails
import com.example.decomposeplayground.domain.repo.AdvertsDatabase

class GetAdvertDetailsUseCase(
        private val database: AdvertsDatabase,
) {

    fun execute(id: Long): AdvertDetails = database.getById(id).run { AdvertDetails(title, text) }
}