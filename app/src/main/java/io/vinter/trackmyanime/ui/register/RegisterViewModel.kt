package io.vinter.trackmyanime.ui.register

import android.annotation.SuppressLint
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.vinter.trackmyanime.network.NetModule
import io.vinter.trackmyanime.network.form.LoginForm
import io.vinter.trackmyanime.network.service.UserService
import retrofit2.HttpException

class RegisterViewModel : ViewModel() {

    var registration = MutableLiveData<String>()
    var error = MutableLiveData<String>()

    @SuppressLint("CheckResult")
    fun register(form: LoginForm) {
        NetModule.animeListModule.create(UserService::class.java)
                .register(form)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response -> registration.postValue(response.message) }, { e ->
                    if (e is HttpException) error.postValue(e.message()) else error.postValue("Problem with internet connection") })
    }

}
