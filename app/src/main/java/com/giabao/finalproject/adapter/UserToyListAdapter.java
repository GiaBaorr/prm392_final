package com.giabao.finalproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.giabao.finalproject.R;
import com.giabao.finalproject.model.ToyEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserToyListAdapter extends BaseAdapter {

    private Context context;
    private List<ToyEntity> toyList;
    private Map<Integer, Integer> cart;

    public UserToyListAdapter(Context context, List<ToyEntity> toyList, Map<Integer, Integer> icart) {
        this.context = context;
        this.toyList = toyList;
        this.cart = icart;
    }

    @Override
    public int getCount() {
        return toyList.size();
    }

    @Override
    public Object getItem(int position) {
        return toyList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.user_item_toy, parent, false);
        }


        ToyEntity toy = toyList.get(position);
        cart.put(toy.getId(), 0);

        ImageView toyImage = convertView.findViewById(R.id.toy_image);
        TextView toyName = convertView.findViewById(R.id.toy_name);
        TextView toyDescription = convertView.findViewById(R.id.toy_description);
        TextView toyPrice = convertView.findViewById(R.id.toy_price);
        TextView toyQuantity = convertView.findViewById(R.id.toy_quantity);
        Button buttonAdd = convertView.findViewById(R.id.button_add);
        Button buttonSubtract = convertView.findViewById(R.id.button_subtract);

        toyName.setText(toy.getName());
        toyDescription.setText(toy.getDescription());
        toyPrice.setText("Price: $" + toy.getPrice());
        toyQuantity.setText(String.valueOf(cart.get(toy.getId())));

        Glide.with(context)
                .load(toy.getImageUrl())
                .into(toyImage);

        buttonAdd.setOnClickListener(v -> {
            if (cart.get(toy.getId()) < toy.getQuantity()) {
                int quantity = cart.get(toy.getId()) + 1;
                toyQuantity.setText(String.valueOf(quantity));
                cart.put(toy.getId(), quantity);
            } else {
                Toast.makeText(context, "You're buying all we have :)", Toast.LENGTH_SHORT).show();
            }
        });

        buttonSubtract.setOnClickListener(v -> {
            if (cart.get(toy.getId()) > 0) {
                int quantity = cart.get(toy.getId()) - 1;
                toyQuantity.setText(String.valueOf(quantity));
                cart.put(toy.getId(), quantity);
            } else {
                cart.remove(toy.getId());
                Toast.makeText(context, "You're buying nothing :)", Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }
}
