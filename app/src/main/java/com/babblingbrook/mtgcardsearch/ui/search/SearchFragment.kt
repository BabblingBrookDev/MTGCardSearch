package com.babblingbrook.mtgcardsearch.ui.search

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.babblingbrook.mtgcardsearch.R
import com.babblingbrook.mtgcardsearch.model.Card
import com.babblingbrook.mtgcardsearch.model.FeedItem
import com.babblingbrook.mtgcardsearch.ui.Status
import com.babblingbrook.mtgcardsearch.ui.adapters.CardAdapter
import com.babblingbrook.mtgcardsearch.ui.adapters.FeedAdapter
import com.babblingbrook.mtgcardsearch.util.CustomTabHelper
import com.babblingbrook.mtgcardsearch.util.getLink
import com.babblingbrook.mtgcardsearch.util.gone
import com.babblingbrook.mtgcardsearch.util.visible
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : Fragment(R.layout.fragment_search), CardAdapter.OnClickListener,
    FeedAdapter.OnClickListener {

    companion object {
        const val BASE_FEED_URL = "https://magic.wizards.com"
    }

    private val viewModel by viewModel<SearchViewModel>()

    private val cardAdapter =
        CardAdapter(listOf(), this)
    private val feedAdapter =
        FeedAdapter(this)

    private var searchJob: Job? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv_cards.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
            adapter = cardAdapter
        }

        rv_feeds.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = feedAdapter
        }

        viewModel.cards.observe(viewLifecycleOwner) {
            when (it) {
                is Status.Success -> {
                    hideStatusViews()
                    cardAdapter.replaceData(it.data)
                }
                is Status.Loading -> {
                    if (cardAdapter.itemCount == 0) showLoading() else hideStatusViews()
                }
                is Status.NoNetwork -> showNoNetwork()

                is Status.Error -> showError()
            }
        }

        viewModel.feedItems.observe(viewLifecycleOwner) { feedList ->
            when (feedList) {
                is Status.Success -> {
                    hideStatusViews()
                    feedAdapter.submitList(feedList.data)
                }
                is Status.Loading -> {
                    hideStatusViews()
                    if (!feedList.data.isNullOrEmpty()) {
                        feedAdapter.submitList(feedList.data)
                    } else {
                        showLoading()
                    }
                }
                is Status.NoNetwork -> {
                    hideStatusViews()
                    if (!feedList.data.isNullOrEmpty()) {
                        hideStatusViews()
                        feedAdapter.submitList(feedList.data)
                    } else {
                        showNoNetwork()
                    }
                }
                is Status.Error -> showError()
            }
        }

        setFeedVisibility(true)

        search_field.doOnTextChanged { text, _, _, _ ->
            text?.let {
                if (it.length < 2) {
                    setFeedVisibility(true)
                    hideStatusViews()
                    cardAdapter.clearData()
                } else {
                    searchJob?.cancel()
                    setFeedVisibility(false)
                    cardAdapter.clearData()
                    searchJob = viewLifecycleOwner.lifecycleScope.launch {
                        delay(500)
                        viewModel.search(it.toString())
                    }
                }
            }
        }
    }

    private fun setFeedVisibility(shouldShow: Boolean) {
        if (shouldShow) {
            rv_feeds.visible()
            rv_cards.gone()
        } else {
            rv_feeds.gone()
            rv_cards.visible()
        }
    }

    override fun onCardRowClicked(view: View, card: Card?) {
        findNavController().navigate(
            SearchFragmentDirections.actionSearchFragmentToDetailFragment(
                card
            )
        )
    }

    override fun onFeedItemClicked(item: FeedItem) {
        val customTab = CustomTabsIntent.Builder()
            .setToolbarColor(ContextCompat.getColor(requireContext(), R.color.colorPrimary))
            .addDefaultShareMenuItem().build()
        val uri = BASE_FEED_URL + getLink(item.description)
        val packageName = CustomTabHelper().getPackageNameToUse(requireContext(), uri)
        if (!packageName.isNullOrEmpty()) {
            customTab.intent.setPackage(packageName)
            customTab.launchUrl(requireContext(), Uri.parse(uri))
        } else {
            val intent = Intent(requireContext(), WebViewActivity::class.java)
            intent.putExtra(WebViewActivity.EXTRA_URL, Uri.parse(uri).toString())
            startActivity(intent)
        }
    }

    private fun showLoading() {
        loading.visible()
        error.gone()
    }

    private fun showNoNetwork() {
        no_network.visible()
        error.gone()
    }

    private fun showError() {
        error.visible()
        loading.gone()
    }

    private fun hideStatusViews() {
        loading.gone()
        error.gone()
        no_network.gone()
    }
}
