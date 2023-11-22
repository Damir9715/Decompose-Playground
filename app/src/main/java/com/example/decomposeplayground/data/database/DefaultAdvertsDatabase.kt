package com.example.decomposeplayground.data.database

import com.example.decomposeplayground.data.database.LorenIpsumGenerator.generate
import com.example.decomposeplayground.data.database.LorenIpsumGenerator.generateSentence
import com.example.decomposeplayground.domain.repo.AdvertsDatabase
import kotlin.random.Random

class DefaultAdvertsDatabase : AdvertsDatabase {

    private val adverts =
            List(50) { index ->
                AdvertData(
                        id = index.toLong() + 1L,
                        title = generate(count = Random.nextInt(3, 7), minWordLength = 3)
                                .joinToString(separator = " ") { it.replaceFirstChar(Char::uppercase) },
                        text = List(50) { generateSentence() }
                                .joinToString(separator = " ")
                )
            }

    override fun getAll(): List<AdvertData> = adverts

    override fun getById(id: Long): AdvertData = adverts.first { it.id == id }
}
