package io.vinter.trackmyanime.ui.main

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import io.vinter.trackmyanime.R
import io.vinter.trackmyanime.ui.login.LoginActivity

class LaunchActivity : AppCompatActivity() {

    internal lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)

        preferences = getSharedPreferences("userPrefs", Context.MODE_PRIVATE)

        if (preferences.getString("token", "") == "") {
            val i = Intent(this, LoginActivity::class.java)
            this.startActivity(i)
            this.finish()
        } else {
            val i = Intent(this, MainActivity::class.java)
            this.startActivity(i)
            this.finish()
        }
    }
}
