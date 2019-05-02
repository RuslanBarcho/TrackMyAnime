package io.vinter.trackmyanime.ui.register

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

import butterknife.ButterKnife
import io.vinter.trackmyanime.R
import io.vinter.trackmyanime.network.form.LoginForm
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var viewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        viewModel = ViewModelProviders.of(this).get(RegisterViewModel::class.java)
        ButterKnife.bind(this)

        val loginField = register_login
        val passwordField = register_password
        val passwordConfirmField = register_confirm_password
        register.setOnClickListener {
            if ((loginField.text.toString() != "") and (passwordField.text.toString() != "")) {
                if (passwordField.text.toString() == passwordConfirmField.text.toString()) {
                    viewModel.register(LoginForm(loginField.text.toString(), passwordField.text.toString()))
                } else {
                    Toast.makeText(this, "Password does not match confirm", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.registration.observe(this, Observer{
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            val intent = Intent()
            setResult(Activity.RESULT_OK, intent)
            finish()
        })

        viewModel.error.observe(this, Observer {
            if (it != null) {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                viewModel.error.postValue(null)
            }
        })
    }
}
