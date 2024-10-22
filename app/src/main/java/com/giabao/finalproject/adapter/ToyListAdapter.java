package com.giabao.finalproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.giabao.finalproject.R;
import com.giabao.finalproject.model.ToyEntity;

import java.util.List;

public class ToyListAdapter extends ArrayAdapter<ToyEntity> {

    private Context context;
    private List<ToyEntity> toys;

    public ToyListAdapter(Context context, List<ToyEntity> toys) {
        super(context, 0, toys);
        this.context = context;
        this.toys = toys;
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
        ImageView toyImage = convertView.findViewById(R.id.toy_image);

        toyName.setText(toy.getName());
        toyDescription.setText(toy.getDescription());
        toyPrice.setText(String.format("$%d", toy.getPrice()));

        // Load the image using Glide
        Glide.with(context)
                .load(toy.getImageUrl())
                .into(toyImage);

        return convertView;
    }

}
