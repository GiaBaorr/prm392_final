package com.giabao.finalproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.giabao.finalproject.R;
import com.giabao.finalproject.config.ApiConfig;
import com.giabao.finalproject.model.UserEntity;
import com.giabao.finalproject.service.IUserService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    Button btn_login;
    EditText txt_email, txt_password;
    TextView txt_signUp;

    IUserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        //Hide ActionBar
        getSupportActionBar().hide();

        txt_email = findViewById(R.id.login_txt_email);
        txt_password = findViewById(R.id.login_txt_password);
        txt_signUp = findViewById(R.id.login_tv_signup);
        btn_login = (Button) findViewById(R.id.login_btn_login);


        txt_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickSignUp();
            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickLogin();
            }
        });
    }

    private void onClickSignUp() {
        Intent signUpIntent = new Intent(MainActivity.this, SignUpActivity.class);
        startActivity(signUpIntent);
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
        IUserService usersApi = ApiConfig.getRetrofitClient().create(IUserService.class);
        Call<List<UserEntity>> call = usersApi.getAllUsers();
        call.enqueue(new Callback<List<UserEntity>>() {
            @Override
            public void onResponse(Call<List<UserEntity>> call, Response<List<UserEntity>> response) {
                UserEntity userEntity = response.body().stream()
                        .filter(x -> x.getUsername().equals(username) && x.getPassword().equals(password))
                        .findFirst().orElse(null);
                if (userEntity == null) {
                    Toast.makeText(getApplicationContext(), "Please check your username & password!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (userEntity.isAdmin()) {
                    Intent adminIntent = new Intent(MainActivity.this, AdminActivity.class);
                    startActivity(adminIntent);
                } else {
                    Intent userIntent = new Intent(MainActivity.this, UserActivity.class);
                    startActivity(userIntent);
                }
            }

            @Override
            public void onFailure(Call<List<UserEntity>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });

    }
}