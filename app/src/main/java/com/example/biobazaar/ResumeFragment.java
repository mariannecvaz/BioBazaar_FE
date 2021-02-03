package com.example.biobazaar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.Image;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.biobazaar.Filters.Product;
import com.example.biobazaar.ShoppingCart.NoShoppingFragment;
import com.example.biobazaar.ShoppingCart.ShoppingCardFragment;
import com.example.biobazaar.User.LoggedFragment;
import com.example.biobazaar.User.Preferences;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ResumeFragment extends Fragment {
    private String subcategory;
    private String category;
    TextView name, payment, userCountry, adress, zipcode, phone, nif, email, totalView,promotionTxt;


    Preferences preferences = new Preferences();
    ShoppingCardFragment cardFr = new ShoppingCardFragment(subcategory, category);

    public ResumeFragment(String subcategory, String category) {
        this.subcategory = subcategory;
        this.category = category;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("WrongConstant")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_resume, container, false);
        preferences.init(getContext());
        getShopping(getContext());
        double ctt = 4.42;
        TextView cttCost = view.findViewById(R.id.cttPrice);
        TextView promotionValue = view.findViewById(R.id.promotion);
        CheckBox checkBox = view.findViewById(R.id.checkBox);
        TextView totalPromotion = view.findViewById(R.id.totalP);
        promotionTxt = view.findViewById(R.id.promotionTxt);
        totalView = view.findViewById(R.id.total);
        NumberFormat nf = new DecimalFormat("0.00");

        if (preferences.getCoins() > 5) {
            checkBox.setAlpha(1);
            totalPromotion.setAlpha(1);
            promotionValue.setAlpha(1);
            promotionTxt.setAlpha(1);
            double promotion = 10;
            double total = preferences.retrieveOrderProductPrice() + ctt;
            double totalP = total - (total / promotion);
            cttCost.setText(nf.format(ctt) + "€");
            promotionValue.setText(nf.format(promotion) + "%");
            totalView.setText(nf.format(total) + "€");
            totalPromotion.setText(nf.format(totalP) + "€");
        } else {
            checkBox.setAlpha(0);
            promotionValue.setAlpha(0);
            totalPromotion.setAlpha(0);
            promotionTxt.setAlpha(0);
            double total = preferences.retrieveOrderProductPrice() + ctt;
            totalView = view.findViewById(R.id.total);
            cttCost.setText(nf.format(ctt) + "€");
            totalView.setText(nf.format(total) + "€");
        }

        ImageButton backBtn = view.findViewById(R.id.backBtn);
        backBtn.setOnClickListener(v -> {
            FragmentTransaction fr1 = getFragmentManager().beginTransaction().setCustomAnimations(
                    R.anim.slide_in_left,  // enter
                    R.anim.slide_out_right);
            fr1.replace(R.id.fragment_container, new payFragment(subcategory, category));
            fr1.commit();
        });

        Button btnFinish = view.findViewById(R.id.btnFinish);
        btnFinish.setOnClickListener(v -> {
            SaveOrders(getContext());
            FragmentTransaction fr1 = getFragmentManager().beginTransaction().setCustomAnimations(
                    R.anim.fade_in_pages,// enter
                    R.anim.fade_out);
            fr1.replace(R.id.fragment_container, new LoggedFragment());
            fr1.commit();
        });

        name = view.findViewById(R.id.name);
        name.setText(preferences.readOrderName());

        email = view.findViewById(R.id.email);
        email.setText(preferences.readOrderEmail());

        userCountry = view.findViewById(R.id.country);
        userCountry.setText(preferences.getOrderCountry());

        adress = view.findViewById(R.id.adress);
        adress.setText(preferences.getOrderAdress());

        zipcode = view.findViewById(R.id.zipCode);
        zipcode.setText(preferences.getOrderZipcode());

        nif = view.findViewById(R.id.nif);
        nif.setText(preferences.getOrderNif());

        phone = view.findViewById(R.id.phone);
        phone.setText(preferences.getOrderPhone());

        payment = view.findViewById(R.id.payment);
        payment.setText(preferences.getOrderPayment());

        return view;
    }

    private void SaveOrders(Context context) {
        final String finalName = name.getText().toString();
        final String finalEmail = email.getText().toString();
        final String finalNif = nif.getText().toString();
        final String finalPhone = phone.getText().toString();
        final String finalCountry = userCountry.getText().toString();
        final String finalAdress = adress.getText().toString();
        final String finalZipCode = zipcode.getText().toString();
        final String finalPayment = payment.getText().toString();
        final String finalTotal = totalView.getText().toString();
        RequestQueue queue = Volley.newRequestQueue(context);

        JSONObject body = new JSONObject();
        try {
            body.put("name", finalName);
            body.put("email", finalEmail);
            body.put("country", finalCountry);
            body.put("adress", finalAdress);
            body.put("zipCode", finalZipCode);
            body.put("nif", finalNif);
            body.put("phone", finalPhone);
            body.put("payment", finalPayment);
            body.put("products", preferences.retrieveOrderProductName());
            body.put("total", finalTotal);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, urlAPI.URL_ORDER + preferences.readEmail(), body,
                response -> {
                    preferences.Logout();
                    Log.d("TAG", "SAVE ORDERS:" + response);
                    editProfile(context);
                    Toast.makeText(getContext(), "Encomenda realizada!", Toast.LENGTH_SHORT).show();
                    getFragmentManager().beginTransaction().replace(R.id.fragment_container, new LoggedFragment()).commit();

                },
                error -> Log.d("TAG", "SaveOrders: " + error)) {
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };
        queue.add(jsonObjectRequest);
    }
    private void getShopping(Context context) {
        final int recyclerContainer = R.id.frameLayoutFavorite;
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonArrayRequest objectRequest = new JsonArrayRequest(Request.Method.GET, urlAPI.URL_CAR + preferences.readEmail(), null,
                response -> {
                    Log.d("tag1", String.valueOf(response.length()));
                    FragmentTransaction fr1 = getFragmentManager().beginTransaction();
                    fr1.replace(recyclerContainer, cardFr);
                    fr1.commit();
                }, error -> Log.d("tagMA", String.valueOf(error)));
        requestQueue.add(objectRequest);
    }
    private void editProfile(Context context) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JSONObject body = new JSONObject();
        try {
            body.put("coins", preferences.getCoins() - 5);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, urlAPI.URL_USER_EDIT + preferences.readEmail(), body,
                response -> {
                    try {
                        String email  = response.getString("email");
                        String password = response.getString("password");
                        String adress = response.getString("adress");
                        String zipCode = response.getString("zipCode");
                        String country = response.getString("country");
                        String city = response.getString("city");
                        String name = response.getString("name");
                        String companyName = response.getString("companyName");
                        String nif = response.getString("nif");
                        String phone = response.getString("phone");
                        int coins = response.getInt("coins");
                        preferences.saveName(name);
                        preferences.saveUserEmail(email);
                        preferences.saveCoins(coins);
                        preferences.saveAdress(adress);
                        preferences.saveZipCode(zipCode);
                        preferences.saveCountry(country);
                        preferences.saveCity(city);
                        preferences.saveNif(nif);
                        preferences.savePhone(phone);
                        preferences.saveCompanyName(companyName);
                        preferences.savePass(password);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Log.d("TAG", "editProfile: " + error)) {
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };

        requestQueue.add(jsonObjectRequest);

    }
}