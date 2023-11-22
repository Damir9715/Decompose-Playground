package com.example.decomposeplayground.domain.repo

import com.example.decomposeplayground.data.database.AdvertData

interface AdvertsDatabase {

    fun getAll(): List<AdvertData>

    fun getById(id: Long): AdvertData
}
