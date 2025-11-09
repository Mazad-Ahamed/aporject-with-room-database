package com.example.projectroom

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch


class ItemViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ItemRepository
    val allItems: LiveData<List<ItemEntity>>

    init {
        val dao = AppDatabase.getDatabase(application).itemDao()
        repository = ItemRepository(dao)
        allItems = repository.allItems
    }

    fun insert(item: ItemEntity) = viewModelScope.launch { repository.insert(item) }
    fun update(item: ItemEntity) = viewModelScope.launch { repository.update(item) }
    fun delete(item: ItemEntity) = viewModelScope.launch { repository.delete(item) }
}