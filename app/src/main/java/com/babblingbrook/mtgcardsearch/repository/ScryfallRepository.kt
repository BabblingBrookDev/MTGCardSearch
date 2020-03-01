package com.babblingbrook.mtgcardsearch.repository

import com.babblingbrook.mtgcardsearch.data.CardDao
import com.babblingbrook.mtgcardsearch.data.Result
import com.babblingbrook.mtgcardsearch.data.ScryfallApi
import com.babblingbrook.mtgcardsearch.model.Card
import com.babblingbrook.mtgcardsearch.model.CardIdentifier
import com.babblingbrook.mtgcardsearch.model.Identifiers
import java.io.IOException
import javax.inject.Inject

class ScryfallRepository @Inject constructor(val scryfallApi: ScryfallApi, val cardDao: CardDao) {

    suspend fun getCards(query: String): Result<List<Card>> {
        val searchResponse = scryfallApi.search(query)
        if (searchResponse.isSuccessful) {
            val body = searchResponse.body()
            if (body != null && body.data.isNotEmpty()) {
                val cardsResponse = scryfallApi.getCards(getCardIdentifiers(body.data))
                if (cardsResponse.isSuccessful) {
                    val cardResponseBody = cardsResponse.body()
                    if (cardResponseBody != null) {
                        return Result.Success(cardResponseBody.data)
                    }
                }
                return Result.Error(IOException("Card search failed ${cardsResponse.code()} ${cardsResponse.message()}"))
            }
        }
        return Result.Error(IOException("Card search failed ${searchResponse.code()} ${searchResponse.message()}"))
    }

    private fun getCardIdentifiers(list: List<String>): CardIdentifier {
        val identifierList = list.map { Identifiers(it) }
        return CardIdentifier(identifierList)
    }
}