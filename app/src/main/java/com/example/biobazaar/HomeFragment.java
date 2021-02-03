package com.example.biobazaar;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.biobazaar.Filters.CategoriesFragment;
import com.example.biobazaar.User.Preferences;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class HomeFragment extends Fragment {
    RecyclerView cardsCatalog;
    TextView nome, price, promotion;
    ImageView imgCard;
    RelativeLayout card;
    Button btnSeeMore;
    String subcategory;

    Preferences preferences = new Preferences();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        card = view.findViewById(R.id.card);
        nome = view.findViewById(R.id.name);
        promotion = view.findViewById(R.id.promotion);
        price = view.findViewById(R.id.price);
        imgCard = view.findViewById(R.id.imgProduct);
        preferences.init(getContext());
        TextView nameTxt = view.findViewById(R.id.welcomeName);

        if (preferences.readUserName() != null) {
            preferences.init(getContext());
            nameTxt.setText(preferences.readUserName());
            Log.d("LOGIN", "Loggada");
        } else {
            nameTxt.setText("");
            preferences.init(getContext());
            Log.d("LOGIN", "HHHHMMM" + preferences.readUserName());
        }

        btnSeeMore = view.findViewById(R.id.btnSeeMore);
        getProducts(getContext());
        return view;
    }

    private void getProducts(Context context) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonArrayRequest objectRequest = new JsonArrayRequest(Request.Method.GET, urlAPI.URL_PRODUCT, null,
                response -> {
                    try {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject productsObjt = response.getJSONObject(i);
                            if (productsObjt.getBoolean("on_sale") != false) {
                                String productName = productsObjt.getString("name").toUpperCase();
                                String productPrice = productsObjt.getString("price").toUpperCase();
                                String productDiscount = productsObjt.getString("discount").toUpperCase();
                                String finalImg = productsObjt.getString("img");
                                Log.d("TAG", "getProducts: " + productName);
                                nome.setText(productName);
                                price.setText(productPrice + "€");
                                promotion.setText(productDiscount);
                                Picasso.get()
                                        .load(finalImg)
                                        .into(imgCard);

                                subcategory = productsObjt.getString("subcategory");
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }, error -> Log.d("tag", String.valueOf(error)));
        requestQueue.add(objectRequest);
        btnSeeMore.setOnClickListener(
                v -> {
                    FragmentTransaction fr1 = getFragmentManager().beginTransaction().setCustomAnimations(
                            R.anim.slide_in_right,  // enter
                            R.anim.slide_out_left);  // exit;
                    fr1.replace(R.id.fragment_container, new CategoriesFragment());
                    fr1.commit();
                });

    }
}