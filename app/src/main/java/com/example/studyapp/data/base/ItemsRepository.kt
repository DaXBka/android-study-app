package com.example.studyapp.data.base

import androidx.lifecycle.MutableLiveData
import com.example.studyapp.data.base.CardDataItem
import com.example.studyapp.data.base.ItemDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class ItemsRepository(private val itemDao: ItemDao) {
    val searchResult = MutableLiveData<List<CardDataItem>>()
    val allItems = itemDao.getItems()

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun insertItem(item: CardDataItem) {
        coroutineScope.launch(Dispatchers.IO) {
            itemDao.insert(item)
        }
    }

    fun deleteItem(item: CardDataItem) {
        coroutineScope.launch(Dispatchers.IO) {
            itemDao.delete(item)
        }
    }

    fun updateItem(item: CardDataItem) {
        coroutineScope.launch(Dispatchers.IO) {
            itemDao.update(item)
        }
    }

    fun getItem(id: Int) {
        coroutineScope.launch(Dispatchers.Main) {
            searchResult.value = asyncFind(id).await()
        }
    }

    private fun asyncFind(id: Int): Deferred<List<CardDataItem>?> =
        coroutineScope.async(Dispatchers.IO) {
            return@async itemDao.getItem(id)
        }
}