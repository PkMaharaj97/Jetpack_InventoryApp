/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.inventory.ui.home

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inventory.data.Item
import com.example.inventory.data.ItemsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

/**
 * ViewModel to retrieve all items in the Room database.
 */

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val itemsRepository:ItemsRepository) : ViewModel() {
    val homeUiState: StateFlow<HomeUiState> = itemsRepository.getAllItemsStream().map {
        HomeUiState(it)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
        initialValue = HomeUiState()
    )
    private val _searchText= MutableStateFlow("")
    val searchText=_searchText.asStateFlow()

    private val _isSearching= MutableStateFlow(false)
    val isSearching=_isSearching.asStateFlow()

    private val _items:StateFlow<List<Item>> = itemsRepository.getAllItemsStream().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
        initialValue = listOf())
    val Items=searchText.
        combine(_items){text,items->
            if(text.isBlank())
            {
                items
            }else{
                items.filter {
                    it.doesMatchSearchQuery(text)
                }
            }
        }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
        initialValue = _items.value

    )
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

fun onSearchTextChanges(text:String){
    _searchText.value=text
}

}

/**
 * Ui State for HomeScreen
 */
data class HomeUiState(val itemList: List<Item> = listOf())
