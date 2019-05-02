package io.vinter.trackmyanime.entity.detail

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Aired {

    @SerializedName("from")
    @Expose
    var from: String? = null
    @SerializedName("to")
    @Expose
    var to: String? = null
    @SerializedName("prop")
    @Expose
    var prop: Prop? = null
    @SerializedName("string")
    @Expose
    var string: String? = null

}
