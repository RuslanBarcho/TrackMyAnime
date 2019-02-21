package io.vinter.trackmyanime.ui.register;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.vinter.trackmyanime.R;
import io.vinter.trackmyanime.network.form.LoginForm;
import io.vinter.trackmyanime.ui.login.LoginViewModel;

public class RegisterActivity extends AppCompatActivity {

    @OnClick(R.id.register)
    void register(){
        if (!loginField.getText().toString().equals("") & !passwordField.getText().toString().equals("")){
            if (passwordField.getText().toString().equals(passwordConfirmField.getText().toString())){
                viewModel.register(new LoginForm(loginField.getText().toString(), passwordField.getText().toString()));
            } else {
                Toast.makeText(this, "Password does not match confirm", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
        }
    }

    @BindView(R.id.register_login)
    EditText loginField;

    @BindView(R.id.register_password)
    EditText passwordField;

    @BindView(R.id.register_confirm_password)
    EditText passwordConfirmField;

    RegisterViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        viewModel = ViewModelProviders.of(this).get(RegisterViewModel.class);
        ButterKnife.bind(this);

        viewModel.registration.observe(this, message -> {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
            finish();
        });
    }
}
