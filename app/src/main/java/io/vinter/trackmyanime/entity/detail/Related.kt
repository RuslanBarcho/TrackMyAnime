package io.vinter.trackmyanime.entity.detail

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Related {

    @SerializedName("Adaptation")
    @Expose
    var adaptation: List<Adaptation>? = null
    @SerializedName("Prequel")
    @Expose
    var prequel: List<Prequel>? = null
    @SerializedName("Summary")
    @Expose
    var summary: List<Summary>? = null
    @SerializedName("Other")
    @Expose
    var other: List<Other>? = null
    @SerializedName("Sequel")
    @Expose
    var sequel: List<Sequel>? = null
    @SerializedName("Alternative setting")
    @Expose
    var alternativeSetting: List<AlternativeSetting>? = null

}
