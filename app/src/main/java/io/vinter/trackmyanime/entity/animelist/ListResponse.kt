package io.vinter.trackmyanime.entity.animelist

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ListResponse {

    @SerializedName("count")
    @Expose
    var count: Int? = null
    @SerializedName("animes")
    @Expose
    var animes: List<AnimeListItem>? = null

}
