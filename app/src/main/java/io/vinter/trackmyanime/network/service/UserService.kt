package io.vinter.trackmyanime.network.service

import io.reactivex.Single
import io.vinter.trackmyanime.entity.user.LoginResponse
import io.vinter.trackmyanime.network.form.LoginForm
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {
    @POST("/user/login")
    fun getToken(@Body form: LoginForm): Single<LoginResponse>

    @POST("/user/signup")
    fun register(@Body form: LoginForm): Single<LoginResponse>
}
