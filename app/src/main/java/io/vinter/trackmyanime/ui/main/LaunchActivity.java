package io.vinter.trackmyanime.ui.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import io.vinter.trackmyanime.R;
import io.vinter.trackmyanime.ui.login.LoginActivity;

public class LaunchActivity extends AppCompatActivity {

    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        preferences = getSharedPreferences("userPrefs", MODE_PRIVATE);

        if (preferences.getString("token","").equals("")) {
            Intent i = new Intent(this, LoginActivity.class);
            this.startActivity(i);
            this.finish();
        } else {
            Intent i = new Intent(this, MainActivity.class);
            this.startActivity(i);
            this.finish();
        }
    }
}
