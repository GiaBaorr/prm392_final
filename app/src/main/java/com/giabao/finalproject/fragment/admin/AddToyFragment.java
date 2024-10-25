package com.giabao.finalproject.fragment.admin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.giabao.finalproject.R;
import com.giabao.finalproject.data.PRMDatabase;
import com.giabao.finalproject.model.ToyEntity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddToyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddToyFragment extends Fragment {

    private EditText imageUrlEditText;
    private EditText toyNameEditText;
    private EditText descriptionEditText;
    private EditText quantityEditText;
    private EditText priceEditText;
    private ImageView toyImageView;
    private Button displayImageButton;
    private Button saveToyButton;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public AddToyFragment() {
        // Required empty public constructor
    }


    public static AddToyFragment newInstance(String param1, String param2) {
        AddToyFragment fragment = new AddToyFragment();
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
        View view = inflater.inflate(R.layout.fragment_add_toy, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        imageUrlEditText = view.findViewById(R.id.add_product_txt_image_url);
        toyNameEditText = view.findViewById(R.id.add_product_txt_title);
        descriptionEditText = view.findViewById(R.id.add_product_txt_description);
        quantityEditText = view.findViewById(R.id.add_product_txt_quantity);
        priceEditText = view.findViewById(R.id.add_product_txt_price);
        toyImageView = view.findViewById(R.id.add_product_iv_image);
        displayImageButton = view.findViewById(R.id.add_product_btn_show_image);
        saveToyButton = view.findViewById(R.id.add_product_btn_save);

        displayImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayImage();
            }
        });

        saveToyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToy();
            }
        });
    }

    private void displayImage() {
        String imageUrl = imageUrlEditText.getText().toString();
        Glide.with(this).load(imageUrl).into(toyImageView);
    }

    private void saveToy() {
        if (!validateFields()) {
            return;
        }

        String name = toyNameEditText.getText().toString();
        String description = descriptionEditText.getText().toString();
        int quantity = Integer.parseInt(quantityEditText.getText().toString());
        int price = Integer.parseInt(priceEditText.getText().toString());
        String imageUrl = imageUrlEditText.getText().toString();

        ToyEntity newToy = new ToyEntity();
        newToy.setName(name);
        newToy.setDescription(description);
        newToy.setQuantity(quantity);
        newToy.setPrice(price);
        newToy.setImageUrl(imageUrl);

        saveToDB(newToy);
    }

    private void saveToDB(ToyEntity newToy) {
        PRMDatabase db = new PRMDatabase(getContext());
        if (!db.insertToy(newToy)) {
            Toast.makeText(this.getContext(), "Error happens", Toast.LENGTH_SHORT).show();
            return;
        }
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.admin_container_boost, new ManageToyFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private boolean validateFields() {
        if (toyNameEditText.getText().toString().isEmpty() ||
                descriptionEditText.getText().toString().isEmpty() ||
                quantityEditText.getText().toString().isEmpty() ||
                priceEditText.getText().toString().isEmpty() ||
                imageUrlEditText.getText().toString().isEmpty()) {

            // Display toast message
            Toast.makeText(this.getContext(), "Please fill in all fields.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}