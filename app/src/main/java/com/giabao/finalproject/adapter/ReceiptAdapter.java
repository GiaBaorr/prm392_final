package com.giabao.finalproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.giabao.finalproject.R;
import com.giabao.finalproject.model.ToyEntity;

import java.util.Map;

public class ReceiptAdapter extends BaseAdapter {
    private final Map<ToyEntity, Integer> cart;
    private final LayoutInflater inflater;

    public ReceiptAdapter(Context context, Map<ToyEntity, Integer> toys) {
        this.cart = toys;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return cart.size();
    }

    @Override
    public Object getItem(int position) {
        return getEntryAtPosition(position).getKey();
    }

    @Override
    public long getItemId(int position) {
        return getEntryAtPosition(position).getKey().getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.receipt_item, parent, false);
        }

        Map.Entry<ToyEntity, Integer> entry = getEntryAtPosition(position);
        ToyEntity toy = entry.getKey();
        int quantity = entry.getValue();

        TextView itemName = convertView.findViewById(R.id.text_item_name);
        TextView itemQuantity = convertView.findViewById(R.id.text_item_quantity);
        TextView itemTotalPrice = convertView.findViewById(R.id.text_item_total_price);

        itemName.setText(toy.getName() + "-" + toy.getPrice() + "$");
        itemQuantity.setText(String.valueOf(quantity));
        itemTotalPrice.setText("$" + (toy.getPrice() * quantity));

        return convertView;
    }

    private Map.Entry<ToyEntity, Integer> getEntryAtPosition(int position) {
        int index = 0;
        for (Map.Entry<ToyEntity, Integer> entry : cart.entrySet()) {
            if (index == position) {
                return entry;
            }
            index++;
        }
        return null;
    }
}
