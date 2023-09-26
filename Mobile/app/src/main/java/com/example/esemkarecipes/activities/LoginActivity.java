package com.example.esemkarecipes.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.view.View;
import android.widget.Toast;

import com.example.esemkarecipes.R;
import com.example.esemkarecipes._Helper;
import com.example.esemkarecipes.databinding.ActivityLoginBinding;
import com.example.esemkarecipes.models.ResponseModel;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        binding.edtUsername.setText("ragiit");
//        binding.edtPassword.setText("argi123");

        binding.edtUsername.getEditText().setText("dkiley3");
        binding.edtPassword.getEditText().setText("uO4tF2");

//        binding.edtUsername.setText("dkiley3");
//        binding.edtPassword.setText("uO4tF2");

        binding.btnLogin.setOnClickListener(v -> {
            String username = binding.edtUsername.getEditText().getText().toString().trim();
            String password = binding.edtPassword.getEditText().getText().toString().trim();
            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please insert the Username & Password!", Toast.LENGTH_SHORT).show();
            } else {
                loginVoid(username, password);
            }
        });
    }

    private void loginVoid(String username, String password) {
        try {
            JSONObject jsonObject = new JSONObject()
                    .put("username", username)
                    .put("password", password);

            ResponseModel responseModel = _Helper.httpHelper("sign-in", jsonObject.toString());
            if (responseModel.code == 200) {
                startActivity(new Intent(this, MainActivity.class));
            } else if (responseModel.code == 404) {
                Toast.makeText(this, "Invalid Username or Password!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, responseModel.data, Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public void clickLogin(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }

    public void clickSignUp(View view) {
        startActivity(new Intent(this, SignUpActivity.class));
    }
}