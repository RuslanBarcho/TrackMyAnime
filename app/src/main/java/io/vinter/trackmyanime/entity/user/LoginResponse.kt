package io.vinter.trackmyanime.entity.user

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class LoginResponse {
    @SerializedName("message")
    @Expose
    var message: String? = null
    @SerializedName("token")
    @Expose
    var token: String? = null

}