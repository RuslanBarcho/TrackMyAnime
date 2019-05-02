package io.vinter.trackmyanime.entity.detail

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Prop {

    @SerializedName("from")
    @Expose
    var from: From? = null
    @SerializedName("to")
    @Expose
    var to: To? = null

}
