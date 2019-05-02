package io.vinter.trackmyanime.entity.top

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import io.vinter.trackmyanime.entity.top.AnimeTop

class Top {

    @SerializedName("request_hash")
    @Expose
    var requestHash: String? = null
    @SerializedName("request_cached")
    @Expose
    var requestCached: Boolean? = null
    @SerializedName("request_cache_expiry")
    @Expose
    var requestCacheExpiry: Int? = null
    @SerializedName("top")
    @Expose
    var top: List<AnimeTop>? = null

}
