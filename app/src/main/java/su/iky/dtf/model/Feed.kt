package su.iky.dtf.model

import com.google.gson.annotations.SerializedName

data class DTFFeedAPI (
    @SerializedName("items") val items: List<Entry>,
    @SerializedName("next") val next: String,
)

data class Entry (
    @SerializedName("author") val author: Author,
    @SerializedName("theme") val theme: Theme,
    @SerializedName("title") val title: String,
    @SerializedName("media") val media: Media,
    @SerializedName("isBlur") val isBlur: Boolean,
    @SerializedName("counters") val counters: Counters
)

data class Author (
    @SerializedName("name") val name: String,
    @SerializedName("avatar") val avatar: String,
)

data class Theme (
    @SerializedName("name") val name: String,
)

data class Media (
    @SerializedName("preview") val preview: String,
    @SerializedName("url") val url: String,
    @SerializedName("type") val type: String,
)

data class Counters (
    @SerializedName("comments") val comments: Int,
    @SerializedName("favorites") val favorites: Int,
    @SerializedName("likes") val likes: Int,
)
