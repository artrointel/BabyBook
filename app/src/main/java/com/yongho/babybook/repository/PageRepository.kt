package com.yongho.babybook.repository

import com.yongho.babybook.data.PageDao
import com.yongho.babybook.data.Page
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PageRepository @Inject constructor(private val pageDao: PageDao) {

    fun getAll(): Flow<Array<Page>> {
        return pageDao.getAll()
    }

    suspend fun getPageByDate(data: String): Page? {
        return pageDao.getPageByDate(data)
    }

    suspend fun insert(page: Page) {
        pageDao.insert(page)
    }

    suspend fun delete(page: Page) {
        pageDao.delete(page)
    }
}