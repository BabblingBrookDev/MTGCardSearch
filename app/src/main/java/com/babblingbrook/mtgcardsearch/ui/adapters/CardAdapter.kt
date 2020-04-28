package com.babblingbrook.mtgcardsearch.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.babblingbrook.mtgcardsearch.databinding.RvCardItemBinding
import com.babblingbrook.mtgcardsearch.model.Card
import kotlinx.android.synthetic.main.rv_card_item.view.*

class CardAdapter(private var items: List<Card>, private val listener: OnClickListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface OnClickListener {
        fun onCardRowClicked(view: View, card: Card?)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return CardViewHolder(
            RvCardItemBinding.inflate(inflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        return (holder as CardViewHolder).bind(items[position], listener)
    }

    fun clearData() {
        items = listOf()
        notifyDataSetChanged()
    }

    fun replaceData(list: List<Card>) {
        items = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = items.size

    class CardViewHolder(private val binding: RvCardItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(card: Card, listener: OnClickListener) {
            binding.card = card
            binding.root.setOnClickListener { listener.onCardRowClicked(itemView.card_image, card) }
            binding.executePendingBindings()
        }
    }
}