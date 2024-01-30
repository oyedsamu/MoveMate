package com.byoyedele.movemate.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.byoyedele.movemate.data.Item
import com.byoyedele.movemate.data.itemList
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn

class SearchViewModel : ViewModel() {
    var searchQuery by mutableStateOf("")
        private set

    var isSearchActive by mutableStateOf(false)
        private set

    val searchResults: StateFlow<List<Item>> =
        snapshotFlow { searchQuery }
            .combine(itemList) { searchQuery, items->
                when {
                    searchQuery.isNotEmpty() -> items.filter { movie ->
                        movie.name.contains(searchQuery, ignoreCase = true)
                    }
                    else -> itemList.first()
                }
            }.stateIn(
                scope = viewModelScope,
                initialValue = emptyList(),
                started = SharingStarted.WhileSubscribed(5_000)
            )

    fun onSearchQueryChange(newQuery: String) {
        searchQuery = newQuery
    }

    fun onActiveChange(changed: Boolean) {
        isSearchActive = changed
    }

    val demoLocation = "Wertheimer, Illinois"
}