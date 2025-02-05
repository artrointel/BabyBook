package com.yongho.babybook.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PageDao {
    @Query("SELECT * FROM page ORDER BY date DESC")
    fun getAll(): Flow<Array<Page>>

    @Query("SELECT * FROM page WHERE date = :date")
    suspend fun getPageByDate(date: String): Page?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(page: Page)

    @Delete
    suspend fun delete(page: Page)
}