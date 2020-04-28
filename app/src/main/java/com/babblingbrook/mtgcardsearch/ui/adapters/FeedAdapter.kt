package com.babblingbrook.mtgcardsearch.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.babblingbrook.mtgcardsearch.databinding.RvFeedItemBinding
import com.babblingbrook.mtgcardsearch.model.FeedItem

class FeedAdapter(private val listener: OnClickListener) :
    ListAdapter<FeedItem, FeedAdapter.FeedViewHolder>(
        FeedDiffCallback()
    ) {

    interface OnClickListener {
        fun onFeedItemClicked(item: FeedItem)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FeedViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return FeedViewHolder(
            RvFeedItemBinding.inflate(inflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        holder.bind(getItem(position), listener)
    }

    class FeedViewHolder(private val binding: RvFeedItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: FeedItem, listener: OnClickListener) {
            binding.feedItem = item
            binding.root.setOnClickListener { listener.onFeedItemClicked(item) }
            binding.executePendingBindings()
        }
    }

    class FeedDiffCallback : DiffUtil.ItemCallback<FeedItem>() {
        override fun areItemsTheSame(oldItem: FeedItem, newItem: FeedItem): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: FeedItem, newItem: FeedItem): Boolean {
            return oldItem == newItem
        }
    }
}