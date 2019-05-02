package io.vinter.trackmyanime.entity.top

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class AnimeTop {
    @SerializedName("mal_id")
    @Expose
    var malId: Int? = null
    @SerializedName("rank")
    @Expose
    var rank: Int? = null
    @SerializedName("title")
    @Expose
    var title: String? = null
    @SerializedName("url")
    @Expose
    var url: String? = null
    @SerializedName("image_url")
    @Expose
    var imageUrl: String? = null
    @SerializedName("type")
    @Expose
    var type: String? = null
    @SerializedName("episodes")
    @Expose
    var episodes: Any? = null
    @SerializedName("start_date")
    @Expose
    var startDate: String? = null
    @SerializedName("end_date")
    @Expose
    var endDate: Any? = null
    @SerializedName("members")
    @Expose
    var members: Int? = null
    @SerializedName("score")
    @Expose
    var score: Double? = null

}
