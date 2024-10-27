package com.giabao.finalproject.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.giabao.finalproject.R;
import com.giabao.finalproject.adapter.ToyListAdapter;
import com.giabao.finalproject.adapter.UserToyListAdapter;
import com.giabao.finalproject.data.PRMDatabase;
import com.giabao.finalproject.model.ToyEntity;
import com.google.android.gms.common.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EventListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserShopFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserShopFragment extends Fragment {

    private ListView listView;
    private UserToyListAdapter toyAdapter;
    private List<ToyEntity> toyList;
    private Button buttonCheckout;
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public UserShopFragment() {
    }


    public static UserShopFragment newInstance(String param1, String param2) {
        UserShopFragment fragment = new UserShopFragment();
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
        getAllToys();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_shop, container, false);

        buttonCheckout = view.findViewById(R.id.button_checkout);
        listView = view.findViewById(R.id.toy_list);
        Map<Integer, Integer> cart = new HashMap<>();

        toyAdapter = new UserToyListAdapter(getContext(), toyList, cart);
        listView.setAdapter(toyAdapter);
        buttonCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                for (Map.Entry<Integer, Integer> entry : cart.entrySet()) {
                    bundle.putInt(String.valueOf(entry.getKey()), entry.getValue());
                }
                Fragment checkoutFragment = new CheckoutFragment();
                checkoutFragment.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.user_container, checkoutFragment)
                        .addToBackStack(null).commit();
            }
        });

        return view;
    }

    private void getAllToys() {
        PRMDatabase db = new PRMDatabase(getContext());
        toyList = db.getAllAvailableToys();
    }


}