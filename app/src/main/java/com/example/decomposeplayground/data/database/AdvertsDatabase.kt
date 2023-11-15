package com.example.decomposeplayground.data.database

interface AdvertsDatabase {

    fun getAll(): List<AdvertEntity>

    fun getById(id: Long): AdvertEntity
}
