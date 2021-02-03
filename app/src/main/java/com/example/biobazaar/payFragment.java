package com.example.biobazaar;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.biobazaar.ShoppingCart.NoShoppingFragment;
import com.example.biobazaar.ShoppingCart.ShoppingFragment;
import com.example.biobazaar.User.LoggedFragment;
import com.example.biobazaar.User.OrderFunctions;
import com.example.biobazaar.User.Preferences;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class payFragment extends Fragment {
    Preferences preferences = new Preferences();
    OrderFunctions orderFunctions = new OrderFunctions();

    EditText name, companyName, userCountry, adress, zipcode, city, phone, nif, email;
    private String subcategory;
    private String category;

    public payFragment(String subcategory, String category) {
        this.subcategory = subcategory;
        this.category = category;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pay, container, false);

        ImageButton btnTransfer = view.findViewById(R.id.btnTransfer);
        ImageButton btnMultibanco = view.findViewById(R.id.btnMultibanco);
        ImageButton btnWay = view.findViewById(R.id.btnWay);
        ImageButton btnMaster = view.findViewById(R.id.btnMaster);

        ImageButton backBtn = view.findViewById(R.id.backBtn);
        backBtn.setOnClickListener(
                v -> {
                    FragmentTransaction fr1 = getFragmentManager().beginTransaction().setCustomAnimations(
                            R.anim.slide_in_left,  // enter
                            R.anim.slide_out_right);
                    fr1.replace(R.id.fragment_container, new ShoppingFragment(subcategory, category));
                    fr1.commit();
                });

        Button btnResume = view.findViewById(R.id.btnResume);
        btnResume.setOnClickListener(
                v -> {
                    saveOrder(getContext());
                    FragmentTransaction fr1 = getFragmentManager().beginTransaction().setCustomAnimations(
                            R.anim.slide_in_left,  // enter
                            R.anim.slide_out_right);
                    fr1.replace(R.id.fragment_container, new ResumeFragment(subcategory, category));
                    fr1.commit();
                });

        btnTransfer.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (v == btnTransfer) {
                    btnWay.setImageResource(R.drawable.ic_way_bw);
                    btnMultibanco.setImageResource(R.drawable.ic_mb_bw);
                    btnMaster.setImageResource(R.drawable.ic_mc_bw);
                    btnTransfer.setImageResource(R.drawable.ic_transferencia2);
                    preferences.saveOrderPayment("Transferência Bancária");
                }
            }
        });

        btnMultibanco.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (v == btnMultibanco) {
                    Log.d("TAG", "onClick: DEU");
                    btnWay.setImageResource(R.drawable.ic_way_bw);
                    btnTransfer.setImageResource(R.drawable.ic_wt_bw);
                    btnMaster.setImageResource(R.drawable.ic_mc_bw);
                    btnMultibanco.setImageResource(R.drawable.ic_multibanco2);
                    preferences.saveOrderPayment("Multibanco");

                }
            }
        });

        btnWay.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (v == btnWay) {
                    Log.d("TAG", "onClick: DEU");
                    btnMultibanco.setImageResource(R.drawable.ic_mb_bw);
                    btnTransfer.setImageResource(R.drawable.ic_wt_bw);
                    btnMaster.setImageResource(R.drawable.ic_mc_bw);
                    btnWay.setImageResource(R.drawable.ic_mbway2);
                    preferences.saveOrderPayment("MB Way");
                }
            }
        });

        btnMaster.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (v == btnMaster) {
                    Log.d("TAG", "onClick: DEU");
                    btnMultibanco.setImageResource(R.drawable.ic_mb_bw);
                    btnTransfer.setImageResource(R.drawable.ic_wt_bw);
                    btnWay.setImageResource(R.drawable.ic_way_bw);
                    btnMaster.setImageResource(R.drawable.ic_mastercard2);
                    preferences.saveOrderPayment("Master Card");

                }
            }
        });

        name = view.findViewById(R.id.firstName);
        name.setText(preferences.readUserName());

        email = view.findViewById(R.id.email);
        email.setText(preferences.readEmail());

        companyName = view.findViewById(R.id.companyName);
        companyName.setText(preferences.getCompanyName());

        userCountry = view.findViewById(R.id.country);
        userCountry.setText(preferences.getCountry());

        adress = view.findViewById(R.id.adress);
        adress.setText(preferences.getAdress());

        zipcode = view.findViewById(R.id.postalCode);
        zipcode.setText(preferences.getZipcode());

        city = view.findViewById(R.id.locality);
        city.setText(preferences.getCity());

        phone = view.findViewById(R.id.phone);
        phone.setText(preferences.getPhone());

        nif = view.findViewById(R.id.nif);
        nif.setText(preferences.getNif());

        return view;
    }

    private void saveOrder(Context context) {
        final String finalName = name.getText().toString();
        final String finalEmail = email.getText().toString();
        final String finalNif = nif.getText().toString();
        final String finalPhone = phone.getText().toString();
        final String finalCompanyName = companyName.getText().toString();
        final String finalCountry = userCountry.getText().toString();
        final String finalAdress = adress.getText().toString();
        final String finalZipCode = zipcode.getText().toString();
        final String finalCity = city.getText().toString();

        preferences.saveOrderName(finalName);
        preferences.saveOrderEmail(finalEmail);
        preferences.saveOrderAdress(finalAdress);
        preferences.saveOrderZipCode(finalZipCode);
        preferences.saveOrderCountry(finalCountry);
        preferences.saveOrderCity(finalCity);
        preferences.saveOrderNif(finalNif);
        preferences.saveOrderPhone(finalPhone);
        preferences.saveOrderCompanyName(finalCompanyName);
    }
}