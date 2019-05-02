package io.vinter.trackmyanime.entity.search

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Search {

    @SerializedName("request_hash")
    @Expose
    var requestHash: String? = null
    @SerializedName("request_cached")
    @Expose
    var requestCached: Boolean? = null
    @SerializedName("request_cache_expiry")
    @Expose
    var requestCacheExpiry: Int? = null
    @SerializedName("results")
    @Expose
    var results: List<Result>? = null
    @SerializedName("last_page")
    @Expose
    var lastPage: Int? = null

}