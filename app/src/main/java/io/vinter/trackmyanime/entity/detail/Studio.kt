package io.vinter.trackmyanime.entity.detail

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Studio {

    @SerializedName("mal_id")
    @Expose
    var malId: Int? = null
    @SerializedName("type")
    @Expose
    var type: String? = null
    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("url")
    @Expose
    var url: String? = null

}
