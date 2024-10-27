package com.giabao.finalproject.activity;

import android.os.Bundle;
import android.view.Menu;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.giabao.finalproject.R;
import com.giabao.finalproject.fragment.MapsFragment;
import com.giabao.finalproject.fragment.UserShopFragment;
import com.giabao.finalproject.model.UserEntity;

public class UserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user);
        initView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.user_menu, menu);
        return true;
    }

    private void initView() {
        boolean displayShop = getIntent().getBooleanExtra("displayShop", false);
        UserEntity userEntity = (UserEntity) getIntent().getSerializableExtra("userEntity");

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Welcome, " + userEntity.getUsername() + "!");
        }
        if (displayShop) {
            getSupportFragmentManager().beginTransaction().replace(R.id.user_container, new UserShopFragment()).commit();
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.user_container, new MapsFragment()).commit();
        }

    }
}