package com.example.biobazaar.User;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.biobazaar.Favorites.FavoriteAdapter;
import com.example.biobazaar.Filters.Product;
import com.example.biobazaar.R;
import com.example.biobazaar.urlAPI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CardOrderFragment extends Fragment {
    RecyclerView cardsOrder;

    List<Product> all_products = new ArrayList<>();

    OrderAdapter adapterC;
    Preferences preferences = new Preferences();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_card_order, container, false);
        getOrders(getContext());
        cardsOrder = view.findViewById(R.id.recyclerFavoriteList);
        cardsOrder.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterC = new OrderAdapter(getContext(), all_products, getFragmentManager());
        cardsOrder.setAdapter(adapterC);

        preferences.init(getContext());
        return view;
    }

    private void getOrders(Context context) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonArrayRequest objectRequest = new JsonArrayRequest(Request.Method.GET, urlAPI.URL_ORDER + preferences.readEmail(), null,
                response -> {
                    try {
                        Log.d("tag1STGH", String.valueOf(response.length()));
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject productsObjt = response.getJSONObject(i);
                            JSONArray products = productsObjt.getJSONArray("products");
                            for (int j = 0; j < products.length(); j++) {
                                String productName = products.getString(i);
                                JsonArrayRequest objectRequest1 = new JsonArrayRequest(Request.Method.GET, urlAPI.URL_PRODUCT + productName, null,
                                        response1 -> {
                                            try {
                                                Log.d("tag1", String.valueOf(response1.length()));
                                                for (int h = 0; h < response1.length(); h++) {
                                                    JSONObject productsObjt1 = response1.getJSONObject(h);
                                                    String productName1 = productsObjt1.getString("name").toUpperCase();
                                                    int productPrice = productsObjt1.getInt("price");
                                                    String finalImg = productsObjt1.getString("img");

                                                    Product product = new Product();
                                                    product.setImg(finalImg);
                                                    product.setPrice(productPrice);
                                                    product.setTitle(productName1);
                                                    all_products.add(product);
                                                    adapterC.notifyDataSetChanged();
                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                                Log.d("tag1", String.valueOf(e));
                                            }
                                        }, error -> Log.d("tagMA", String.valueOf(error)));
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.d("tag1", String.valueOf(e));
                    }
                }, error -> Log.d("tagMA", String.valueOf(error)));
        requestQueue.add(objectRequest);

    }

}