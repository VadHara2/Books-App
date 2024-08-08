package com.books.app.data.model

import com.google.gson.annotations.SerializedName


data class Root(
    @SerializedName("books") val books: List<Book>,
    @SerializedName("top_banner_slides") val banners: List<Banner>,
    @SerializedName("you_will_like_section") val youWillLikeSection: List<BookId>,
    @SerializedName("details_carousel") val detailsCarousel: List<Book>
)

