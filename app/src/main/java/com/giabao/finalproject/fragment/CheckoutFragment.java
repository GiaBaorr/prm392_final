package com.giabao.finalproject.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.giabao.finalproject.R;
import com.giabao.finalproject.adapter.ReceiptAdapter;
import com.giabao.finalproject.data.PRMDatabase;
import com.giabao.finalproject.model.ToyEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CheckoutFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CheckoutFragment extends Fragment {

    private List<ToyEntity> toyList;
    private ListView listView;
    private TextView textTotal;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public CheckoutFragment() {
    }

    public static CheckoutFragment newInstance(String param1, String param2) {
        CheckoutFragment fragment = new CheckoutFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_checkout, container, false);
        getAllToys();
        listView = view.findViewById(R.id.list_receipt_items);
        textTotal = view.findViewById(R.id.text_total);
        int total = 0;

        Bundle bundle = getArguments();
        Map<ToyEntity, Integer> cart = new HashMap<>();
        if (bundle != null) {
            for (String key : bundle.keySet()) {
                int value = bundle.getInt(key);
                ToyEntity toy = getToyById(key);
                if (toy != null && value > 0) {
                    total += toy.getPrice() * value;
                    cart.put(toy, value);
                }
            }
            textTotal.setText("Total: $" + total);
            ReceiptAdapter adapter = new ReceiptAdapter(getContext(), cart);
            listView.setAdapter(adapter);
        }


        return view;
    }

    private void getAllToys() {
        PRMDatabase db = new PRMDatabase(getContext());
        toyList = db.getAllAvailableToys();
    }

    private ToyEntity getToyById(String id) {

        for (ToyEntity toy : toyList) {
            if (toy.getId() == Integer.parseInt(id)) {
                return toy;
            }
        }
        return null;
    }
}