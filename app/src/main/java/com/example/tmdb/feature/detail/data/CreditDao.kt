package com.example.tmdb.feature.detail.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy

//TODO Unused
@Dao
interface CreditDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCredit(credit: CreditEntity)

}