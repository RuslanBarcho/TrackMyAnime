package io.vinter.trackmyanime.ui.login

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

import io.vinter.trackmyanime.R
import io.vinter.trackmyanime.network.form.LoginForm
import io.vinter.trackmyanime.ui.main.MainActivity
import io.vinter.trackmyanime.ui.register.RegisterActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)

        val loginButton = login_button
        val loginField = login_login
        val passwordField = login_password
        val registerButton = register_button

        loginButton.setOnClickListener {
            viewModel.getToken(LoginForm(loginField.text.toString(), passwordField.text.toString()))
        }

        registerButton.setOnClickListener {
            val switchToRegister = Intent(this, RegisterActivity::class.java)
            this.startActivityForResult(switchToRegister, 1)
        }

        viewModel.token.observe(this, Observer {
            if (it != null) {
                val switchToMain = Intent(this, MainActivity::class.java)
                val preferences = getSharedPreferences("userPrefs", Context.MODE_PRIVATE)
                preferences.edit().putString("token", it).apply()
                this.startActivity(switchToMain)
                finish()
            }
        })

        viewModel.state.observe(this, Observer {
            when (it) {
                0 -> loginButton.text = "Login"
                1 -> loginButton.text = "Loading"
            }
        })

        viewModel.error.observe(this, Observer {
            if (it != null) {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                viewModel.error.postValue(null)
            }
        })
    }
}
