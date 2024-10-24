package com.giabao.finalproject.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.giabao.finalproject.R;
import com.giabao.finalproject.fragment.MapsFragment;
import com.giabao.finalproject.fragment.admin.ManageToyFragment;

public class AdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin);


        initView();
    }

    private void initView() {
        // Change the title of the ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Admin Dashboard");  // Set your custom title
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.admin_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.manage_toy) {
            getSupportFragmentManager().beginTransaction().replace(R.id.admin_container_boost, new ManageToyFragment()).commit();
        } else if (itemId == R.id.maps) {
            getSupportFragmentManager().beginTransaction().replace(R.id.admin_container_boost, new MapsFragment()).commit();
        }

        return true;
    }
}