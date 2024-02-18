package com.pacgi.android.travelr

import android.app.DownloadManager.Query
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn


class MainViewModel: ViewModel() {
    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _destinations = MutableStateFlow(allDestinations)
    val destinations = searchText
        .combine(_destinations) { text, destinations ->
            if (text.isBlank()) {
                destinations
            } else {
                destinations.filter {
                    it.matchesSearchQuery(text)
                }
            }

        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _destinations.value
        )

    fun _onSearchTextChange(text:String) {
        _searchText.value = text
    }
}

data class Destination(
    val destName: String,
    val hasVisited: Boolean,
    val nextVisit: String,
    val prevVisit: String,
)
{
    fun matchesSearchQuery(query: String): Boolean {
        val matchingValues = listOf(
            destName,
            "{$destName.first()}"
        )
        return matchingValues.any {
            it.contains(query, ignoreCase = true)
        }
    }
}

private val allDestinations = listOf(
    Destination(
        destName = "Prague",
        hasVisited = true,
        nextVisit = "06/03/2023",
        prevVisit = "01/02/2019",
    ),
    Destination(
        destName = "Sicily",
        hasVisited = false,
        nextVisit = "",
        prevVisit = "",
    )
)