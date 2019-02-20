package io.vinter.trackmyanime.network.service;

import io.reactivex.Single;
import io.vinter.trackmyanime.entity.user.LoginResponse;
import io.vinter.trackmyanime.network.form.LoginForm;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserService {
    @POST("/user/login")
    Single<LoginResponse> getToken(@Body LoginForm form);
}
