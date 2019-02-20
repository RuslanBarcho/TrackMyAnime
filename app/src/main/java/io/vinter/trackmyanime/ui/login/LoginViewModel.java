package io.vinter.trackmyanime.ui.login;

import android.annotation.SuppressLint;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.vinter.trackmyanime.network.NetModule;
import io.vinter.trackmyanime.network.form.LoginForm;
import io.vinter.trackmyanime.network.service.UserService;

public class LoginViewModel extends ViewModel{

    public MutableLiveData<String> token = new MutableLiveData<>();

    @SuppressLint("CheckResult")
    public void getToken(LoginForm form){
        NetModule.getAnimeListModule().create(UserService.class)
                .getToken(form)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(loginResponse -> {
                    token.postValue(loginResponse.getToken());
                }, e -> {
                    Log.e("Network", e.getMessage());
                });
    }

}
