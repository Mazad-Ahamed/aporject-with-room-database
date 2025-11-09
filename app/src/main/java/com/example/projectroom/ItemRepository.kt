package com.example.projectroom

import androidx.lifecycle.LiveData

class ItemRepository(private val itemDao: ItemDao) {
    val allItems: LiveData<List<ItemEntity>> = itemDao.getAllItems()

    suspend fun insert(item: ItemEntity) = itemDao.insertItem(item)
    suspend fun update(item: ItemEntity) = itemDao.updateItem(item)
    suspend fun delete(item: ItemEntity) = itemDao.deleteItem(item)
}