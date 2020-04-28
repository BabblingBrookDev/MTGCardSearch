package com.babblingbrook.mtgcardsearch.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.babblingbrook.mtgcardsearch.model.FeedItem

@Dao
interface FeedDao {
    @Query("SELECT * FROM FeedItem")
    suspend fun loadFeedItems(): List<FeedItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFeedItem(feedItem: FeedItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFeedItems(feedItems: List<FeedItem>)

    @Query("DELETE FROM FeedItem")
    fun deleteAllFeedItems()

    @Transaction
    suspend fun deleteAndReplaceFeedItems(feedItems: List<FeedItem>) {
        deleteAllFeedItems()
        insertFeedItems(feedItems)
    }
}