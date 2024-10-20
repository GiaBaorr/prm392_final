package com.giabao.finalproject.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.giabao.finalproject.R;
import com.giabao.finalproject.config.ApiConfig;
import com.giabao.finalproject.model.UserEntity;
import com.giabao.finalproject.service.IUserService;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    Button btn_login;
    EditText txt_email, txt_password;

    IUserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        txt_email = findViewById(R.id.login_txt_email);
        txt_password = findViewById(R.id.login_txt_password);
        btn_login = (Button) findViewById(R.id.login_btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickLogin();
            }
        });
    }

    private void onClickLogin() {
        String email = txt_email.getText().toString();
        String password = txt_password.getText().toString();
        if (!email.isEmpty() && !password.isEmpty()) {
            checkLogin(email, password);
        } else {
            Toast.makeText(getApplicationContext(), "Input your username and password", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkLogin(String username, String password) {
        Retrofit retrofit = new Retrofit
                .Builder()
                .baseUrl(ApiConfig.baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IUserService usersApi = retrofit.create(IUserService.class);
        Call<List<UserEntity>> call = usersApi.getAllUsers();
        call.enqueue(new Callback<List<UserEntity>>() {
            @Override
            public void onResponse(Call<List<UserEntity>> call, Response<List<UserEntity>> response) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<List<UserEntity>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });

    }
}