package com.example.searchsharedflow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {

    private val allNames = listOf("Mona", "Moon", "Zazo", "Ahmed", "Ali", "Sara")

    private val _searchQuery = MutableSharedFlow<String>(replay = 1)

    val filteredNames: StateFlow<List<String>> = _searchQuery
        .map { query ->
            if (query.isBlank()) {
                allNames
            } else {
                allNames.filter { name ->
                    name.startsWith(query, ignoreCase = true)
                }
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = allNames
        )

    fun onSearchQueryChanged(newQuery: String) {
        viewModelScope.launch {
            _searchQuery.emit(newQuery)
        }
    }
}