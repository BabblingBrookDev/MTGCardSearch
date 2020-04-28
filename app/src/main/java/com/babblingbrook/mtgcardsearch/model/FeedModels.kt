package com.babblingbrook.mtgcardsearch.model

import android.os.Build
import android.text.Html
import android.widget.ImageView
import androidx.core.text.HtmlCompat
import androidx.databinding.BindingAdapter
import androidx.room.Entity
import androidx.room.PrimaryKey
import coil.api.load
import com.google.android.material.textview.MaterialTextView
import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root
import java.util.regex.Pattern

@Root(name = "rss", strict = false)
data class Feed @JvmOverloads constructor(
    @field:Element var channel: Channel = Channel()
)

@Root(name = "channel", strict = false)
data class Channel @JvmOverloads constructor(
    @field:ElementList(inline = true, name = "item")
    var item: MutableList<FeedItem> = mutableListOf()
)

@Entity
@Root(name = "item", strict = false)
data class FeedItem @JvmOverloads constructor(
    @PrimaryKey
    @field:Element(name = "title")
    var title: String = "",
    @field:Element(name = "description")
    var description: String = "",
    @field:Element(name = "pubDate")
    var pubDate: String = "",
    @field:Element(name = "link")
    var link: String = ""
)

@BindingAdapter("feedDesc")
fun MaterialTextView.parseFeedDescription(string: String) {
    val pattern = Pattern.compile("(?<=<p>)(.*?)(?=</p>)")
    val matcher = pattern.matcher(string as CharSequence)
    var match: String? = null
    if (matcher.find()) {
        match = matcher.group(0)
    }
    match?.let {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            this.text = Html.fromHtml(match, HtmlCompat.FROM_HTML_MODE_LEGACY)
        } else {
            this.text = Html.fromHtml(match)
        }
    }
}

@BindingAdapter("feedImageLink")
fun ImageView.parseFeedImageLink(string: String) {
    val pattern = Pattern.compile("src\\s*=\\s*([\"'])?([^\"']*)")
    val matcher = pattern.matcher(string as CharSequence)
    var match: String? = null
    if (matcher.find()) {
        match = matcher.group(2)
    }
    this.load(match)
}