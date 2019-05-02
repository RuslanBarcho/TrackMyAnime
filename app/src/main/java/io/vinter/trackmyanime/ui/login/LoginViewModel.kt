package io.vinter.trackmyanime.ui.login

import android.annotation.SuppressLint
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.vinter.trackmyanime.network.NetModule
import io.vinter.trackmyanime.network.form.LoginForm
import io.vinter.trackmyanime.network.service.UserService
import retrofit2.HttpException

class LoginViewModel : ViewModel() {

    var token = MutableLiveData<String>()
    var error = MutableLiveData<String>()
    var state = MutableLiveData<Int>()

    @SuppressLint("CheckResult")
    fun getToken(form: LoginForm) {
        state.postValue(1)
        NetModule.animeListModule.create(UserService::class.java)
                .getToken(form)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ loginResponse ->
                    state.postValue(0)
                    token.postValue(loginResponse.token)
                }, { e ->
                    state.postValue(0)
                    if (e is HttpException) error.postValue(e.message()) else error.postValue("Problem with internet connection")
                })
    }

}
