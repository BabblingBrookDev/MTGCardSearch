package com.babblingbrook.mtgcardsearch.ui.search

import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.babblingbrook.mtgcardsearch.R
import com.babblingbrook.mtgcardsearch.model.FeedItem
import com.babblingbrook.mtgcardsearch.util.getDescription
import com.babblingbrook.mtgcardsearch.util.getImageLink
import kotlinx.android.synthetic.main.rv_feed_item.view.*

class FeedAdapter(private val listener: OnClickListener) :
    ListAdapter<FeedItem, FeedAdapter.FeedViewHolder>(FeedDiffCallback()) {

    interface OnClickListener {
        fun onFeedItemClicked(item: FeedItem)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FeedViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return FeedViewHolder(inflater.inflate(R.layout.rv_feed_item, parent, false))
    }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        holder.bind(getItem(position), listener)
    }

    class FeedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: FeedItem,  listener: OnClickListener) {
            itemView.article_image.load(item.link)
            val imageLink = getImageLink(item.description)
            itemView.article_image.load(imageLink)
            itemView.article_desc.text = Html.fromHtml(getDescription(item.description))
            itemView.article_title.text = item.title
            itemView.article_date.text = item.pubDate

            itemView.setOnClickListener {
                listener.onFeedItemClicked(item)
            }
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