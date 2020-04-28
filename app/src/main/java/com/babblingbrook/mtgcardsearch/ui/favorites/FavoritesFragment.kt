package com.babblingbrook.mtgcardsearch.ui.favorites

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.babblingbrook.mtgcardsearch.R
import com.babblingbrook.mtgcardsearch.model.Card
import com.babblingbrook.mtgcardsearch.ui.adapters.CardAdapter
import kotlinx.android.synthetic.main.fragment_favorites.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoritesFragment : Fragment(R.layout.fragment_favorites), CardAdapter.OnClickListener {

    private val viewModel by viewModel<FavoritesViewModel>()

    private val favoritesAdapter =
        CardAdapter(listOf(), this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv_favorites.layoutManager = LinearLayoutManager(requireContext())
        rv_favorites.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                LinearLayoutManager.VERTICAL
            )
        )
        rv_favorites.adapter = favoritesAdapter
        viewModel.cards.observe(viewLifecycleOwner) {
            favoritesAdapter.replaceData(it)
        }

        toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onCardRowClicked(view: View, card: Card?) {
        val action = FavoritesFragmentDirections.actionFavoritesFragmentToDetailFragment(card)
        findNavController().navigate(action)
    }
}
