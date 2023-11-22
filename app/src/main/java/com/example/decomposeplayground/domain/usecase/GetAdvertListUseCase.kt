package com.example.decomposeplayground.domain.usecase

import com.example.decomposeplayground.domain.repo.AdvertsDatabase
import com.example.decomposeplayground.domain.model.Advert

class GetAdvertListUseCase(
        private val database: AdvertsDatabase,
) {

    fun execute(): List<Advert> = database.getAll().map { Advert(it.id, it.title) }
}