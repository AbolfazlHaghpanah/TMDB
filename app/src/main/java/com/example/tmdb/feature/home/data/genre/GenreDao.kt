package com.example.tmdb.feature.home.data.genre

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface GenreDao {

    @Query("SELECT * FROM GENRE")
    fun observeGenre(): List<GenreEntity>

    @Query("SELECT genre FROM genre WHERE ID IS :id")
    fun findGenre(id: Int): String

    @Insert
    fun addGenre(genre: GenreEntity)
}