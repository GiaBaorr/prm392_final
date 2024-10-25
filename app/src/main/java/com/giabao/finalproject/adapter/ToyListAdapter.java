package com.giabao.finalproject.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.giabao.finalproject.R;
import com.giabao.finalproject.data.PRMDatabase;
import com.giabao.finalproject.fragment.admin.AddToyFragment;
import com.giabao.finalproject.model.ToyEntity;

import java.util.List;

public class ToyListAdapter extends ArrayAdapter<ToyEntity> {

    private Context context;
    private List<ToyEntity> toys;
    private PRMDatabase db;

    public ToyListAdapter(Context context, List<ToyEntity> toys) {
        super(context, 0, toys);
        this.context = context;
        this.toys = toys;
        db = new PRMDatabase(getContext());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_toy, parent, false);
        }

        ToyEntity toy = toys.get(position);

        TextView toyName = convertView.findViewById(R.id.toy_name);
        TextView toyDescription = convertView.findViewById(R.id.toy_description);
        TextView toyPrice = convertView.findViewById(R.id.toy_price);
        TextView toyQuantity = convertView.findViewById(R.id.toy_quantity);
        ImageView toyImage = convertView.findViewById(R.id.toy_image);
        Button btnEditToy = convertView.findViewById(R.id.btn_edit_toy);
        Button btnDeleteToy = convertView.findViewById(R.id.btn_delete_toy);

        toyName.setText(toy.getName());
        toyDescription.setText(toy.getDescription());
        toyQuantity.setText(String.format("Quantity: %d", toy.getQuantity()));
        toyPrice.setText(String.format("$%d", toy.getPrice()));

        // Load the image using Glide
        Glide.with(context)
                .load(toy.getImageUrl())
                .into(toyImage);

        btnEditToy.setOnClickListener(v -> {
            AddToyFragment fragment = new AddToyFragment();
            Bundle args = new Bundle();
            args.putSerializable("toy", toy);
            fragment.setArguments(args);
            FragmentTransaction transaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.admin_container_boost, fragment).commit();
        });

        btnDeleteToy.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Confirm Deletion")
                    .setMessage("Are you sure you want to delete this toy?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        // Code to delete the toy
                        if (db.deleteToy(toy.getId())) {
                            toys.remove(position);
                            notifyDataSetChanged();
                            Toast.makeText(context, "Deleted " + toy.getName(), Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Toast.makeText(context, "Error while deleting: " + toy.getName(), Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("No", null)
                    .show();
        });

        return convertView;
    }

}
