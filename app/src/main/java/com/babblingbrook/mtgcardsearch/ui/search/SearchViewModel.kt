package com.babblingbrook.mtgcardsearch.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.babblingbrook.mtgcardsearch.model.Card
import com.babblingbrook.mtgcardsearch.model.FeedItem
import com.babblingbrook.mtgcardsearch.repository.Repository
import com.babblingbrook.mtgcardsearch.ui.Status
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchViewModel(private val repository: Repository) : ViewModel() {

    private var feedSource: LiveData<Status<List<FeedItem>>> = MutableLiveData()
    private val _feedItems = MediatorLiveData<Status<List<FeedItem>>>()
    val feedItems: LiveData<Status<List<FeedItem>>> get() = _feedItems
    private val query = MutableLiveData<String>()

    init {
        getFeedData()
    }

    private fun getFeedData() = viewModelScope.launch(Dispatchers.Main) {
        _feedItems.removeSource(feedSource)
        withContext(Dispatchers.IO) {
            feedSource = repository.getFeed()
        }
        _feedItems.addSource(feedSource) {
            _feedItems.value = it
        }
    }

    fun search(value: String) {
        query.value = value
    }

    val cards: LiveData<Status<List<Card>>> =
        Transformations.switchMap(query) { query -> repository.search(query).asLiveData() }
}
