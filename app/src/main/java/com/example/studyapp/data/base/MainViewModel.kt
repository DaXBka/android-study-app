package com.example.studyapp.data.base

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.studyapp.data.base.CardDataItem
import com.example.studyapp.data.base.InventoryDatabase
import com.example.studyapp.data.base.ItemsRepository

class MainViewModel(app: Application): ViewModel() {
    val allItems: LiveData<List<CardDataItem>>
    val searchResult: MutableLiveData<List<CardDataItem>>
    private val repository: ItemsRepository

    init {
        val productDb = InventoryDatabase.getInstance(app)
        val productDao = productDb.itemDao()

        repository = ItemsRepository(productDao)
        allItems = repository.allItems
        searchResult = repository.searchResult
    }

    fun insertItem(item: CardDataItem) {
        repository.insertItem(item)
    }

    fun deleteItem(item: CardDataItem) {
        repository.deleteItem(item)
    }

    fun updateItem(item: CardDataItem) {
        repository.updateItem(item)
    }
}