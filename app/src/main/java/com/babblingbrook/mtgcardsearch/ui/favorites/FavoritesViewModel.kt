package com.babblingbrook.mtgcardsearch.ui.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.babblingbrook.mtgcardsearch.model.Card
import com.babblingbrook.mtgcardsearch.repository.ScryfallRepository
import com.babblingbrook.mtgcardsearch.ui.Status

class FavoritesViewModel(private val scryfallRepository: ScryfallRepository) : ViewModel() {

    var cards: LiveData<Status<List<Card>>> = scryfallRepository.getFavorites().asLiveData()
}