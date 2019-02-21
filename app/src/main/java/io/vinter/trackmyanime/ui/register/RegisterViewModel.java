package io.vinter.trackmyanime.ui.register;

import android.annotation.SuppressLint;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.vinter.trackmyanime.network.NetModule;
import io.vinter.trackmyanime.network.form.LoginForm;
import io.vinter.trackmyanime.network.service.UserService;

public class RegisterViewModel extends ViewModel{

    public MutableLiveData<String> registration = new MutableLiveData<>();

    @SuppressLint("CheckResult")
    public void register(LoginForm form){
        NetModule.getAnimeListModule().create(UserService.class)
                .register(form)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    registration.postValue(response.getMessage());
                }, e-> {
                    Log.e("Network", e.getMessage());
                });
    }

}
