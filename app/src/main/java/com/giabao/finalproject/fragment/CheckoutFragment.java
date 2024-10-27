package com.giabao.finalproject.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.giabao.finalproject.R;
import com.giabao.finalproject.adapter.ReceiptAdapter;
import com.giabao.finalproject.data.PRMDatabase;
import com.giabao.finalproject.model.ToyEntity;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import vn.payos.PayOS;
import vn.payos.type.CheckoutResponseData;
import vn.payos.type.ItemData;
import vn.payos.type.PaymentData;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CheckoutFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CheckoutFragment extends Fragment {

    private List<ToyEntity> toyList;
    private ListView listView;
    private TextView textTotal;
    private Button openLinkButton;
    private String payUrl = "";

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
        openLinkButton = view.findViewById(R.id.button_pay);
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
            try {
                generatePayment(total);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            ReceiptAdapter adapter = new ReceiptAdapter(getContext(), cart);
            listView.setAdapter(adapter);
        }

        openLinkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = payUrl;
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

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

    private void generatePayment(int total) throws Exception {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String clientId = "";
                    String apiKey = "";
                    String checksumKey = "";
                    PayOS payOS = new PayOS(clientId, apiKey, checksumKey);

                    ItemData itemData = ItemData.builder().name("Mỳ tôm Hảo Hảo ly").quantity(1).price(2000).build();
                    String currentTimeString = String.valueOf(String.valueOf(new Date().getTime()));
                    long orderCode = Long.parseLong(currentTimeString.substring(currentTimeString.length() - 6));
                    PaymentData paymentData = PaymentData.builder().orderCode(orderCode).amount(total)
                            .description("Order").returnUrl("https://github.com/").cancelUrl("https://github.com/")
                            .item(itemData).build();
                    CheckoutResponseData result = payOS.createPaymentLink(paymentData);
                    payUrl = result.getCheckoutUrl();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }
}