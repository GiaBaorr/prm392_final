package com.giabao.finalproject.activity;

import android.content.Intent;
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

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    EditText usernameView, pwd1View, pwd2View;
    Button signupBtn;
    IUserService usersApi;
    List<UserEntity> existUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initView();
    }

    private void initView() {
        usersApi = ApiConfig.getRetrofitClient().create(IUserService.class);
        //Hide ActionBar
        getSupportActionBar().hide();
        fetchAllUser();

        usernameView = findViewById(R.id.et_username);
        pwd1View = findViewById(R.id.et_password);
        pwd2View = findViewById(R.id.et_confirm_password);
        signupBtn = findViewById(R.id.btn_signup);

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickSignUp();
            }
        });
    }

    private void fetchAllUser() {
        Call<List<UserEntity>> call = usersApi.getAllUsers();
        call.enqueue(new Callback<List<UserEntity>>() {
            @Override
            public void onResponse(Call<List<UserEntity>> call, Response<List<UserEntity>> response) {
                existUser = response.body();
            }

            @Override
            public void onFailure(Call<List<UserEntity>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void onClickSignUp() {
        String username = this.usernameView.getText().toString();
        String pwd1 = this.pwd1View.getText().toString();
        String pwd2 = this.pwd2View.getText().toString();
        if (!validate(username, pwd1, pwd2)) {
            return;
        }
        if (existUser.stream().anyMatch(x -> x.getUsername().equals(username))) {
            Toast.makeText(getApplicationContext(), "Username already exists", Toast.LENGTH_SHORT).show();
            return;
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(username);
        userEntity.setPassword(pwd1);
        userEntity.setAdmin(false);

        signUpCall(username, pwd1);
    }

    private void signUpCall(String username, String pwd1) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(username);
        userEntity.setPassword(pwd1);
        userEntity.setAdmin(false);
        Call<UserEntity> call = usersApi.create(userEntity);
        call.enqueue(new Callback<UserEntity>() {
            @Override
            public void onResponse(Call<UserEntity> call, Response<UserEntity> response) {
                Intent userIntent = new Intent(SignUpActivity.this, UserActivity.class);
                startActivity(userIntent);
            }

            @Override
            public void onFailure(Call<UserEntity> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Service error!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validate(String username, String pwd1, String pwd2) {
        if (username.isEmpty() || pwd1.isEmpty() || pwd2.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please input all required fields", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!pwd1.equals(pwd2)) {
            Toast.makeText(getApplicationContext(), "Your passwords do not match", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}