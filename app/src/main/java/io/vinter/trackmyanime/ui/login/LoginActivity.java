package io.vinter.trackmyanime.ui.login;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.vinter.trackmyanime.R;
import io.vinter.trackmyanime.network.form.LoginForm;
import io.vinter.trackmyanime.ui.main.MainActivity;
import io.vinter.trackmyanime.ui.main.MainViewModel;
import io.vinter.trackmyanime.ui.register.RegisterActivity;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.login_login)
    EditText loginField;

    @BindView(R.id.login_password)
    EditText passwordField;

    @BindView(R.id.login_button)
    Button loginButton;

    @OnClick(R.id.login_button)
    public void login(){
        viewModel.getToken(new LoginForm(loginField.getText().toString(), passwordField.getText().toString()));
    }

    @OnClick(R.id.register_button)
    void launchRegister(){
        Intent switchToRegister = new Intent(this, RegisterActivity.class);
        this.startActivityForResult(switchToRegister, 1);
    }

    LoginViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        viewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        ButterKnife.bind(this);

        viewModel.token.observe(this, token -> {
            if (token != null){
                Intent switchToMain = new Intent(this, MainActivity.class);
                SharedPreferences preferences = getSharedPreferences("userPrefs", MODE_PRIVATE);
                preferences.edit().putString("token", token).apply();
                this.startActivity(switchToMain);
                finish();
            }
        });
    }
}
