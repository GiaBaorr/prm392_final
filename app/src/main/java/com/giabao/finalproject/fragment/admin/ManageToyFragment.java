package com.giabao.finalproject.fragment.admin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.giabao.finalproject.R;
import com.giabao.finalproject.adapter.ToyListAdapter;
import com.giabao.finalproject.data.PRMDatabase;
import com.giabao.finalproject.model.ToyEntity;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ManageToyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ManageToyFragment extends Fragment {

    ListView toysView;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public ManageToyFragment() {
        // Required empty public constructor
    }


    public static ManageToyFragment newInstance(String param1, String param2) {
        ManageToyFragment fragment = new ManageToyFragment();
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
        View view = inflater.inflate(R.layout.fragment_manage_toy, container, false);
        toysView = view.findViewById(R.id.manage_toy_list);
        getAllToys();

        return view;
    }

    private void getAllToys() {
        PRMDatabase db = new PRMDatabase(getContext());
        List<ToyEntity> toys = db.getAllToys();

        ToyListAdapter adapter = new ToyListAdapter(getContext(), toys);
        toysView.setAdapter(adapter);
    }


}