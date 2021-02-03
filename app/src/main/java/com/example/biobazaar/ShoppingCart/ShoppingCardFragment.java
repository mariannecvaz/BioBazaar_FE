package com.example.biobazaar.ShoppingCart;

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
import com.example.biobazaar.Favorites.FavoriteFunction;
import com.example.biobazaar.Filters.Product;
import com.example.biobazaar.R;
import com.example.biobazaar.User.Preferences;
import com.example.biobazaar.urlAPI;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class ShoppingCardFragment extends Fragment {
    private final String subcategory;
    private final String category;

    RecyclerView cardsShop;
    ShoppingAdapter adapter;
    public List<Product> all_products = new ArrayList<>();
    Preferences preferences = new Preferences();
    public ShoppingCardFragment(String subcategory, String category) {
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
        View view = inflater.inflate(R.layout.fragment_shopping_card, container, false);
        cardsShop = view.findViewById(R.id.recyclerShoppingList);
        cardsShop.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ShoppingAdapter(getContext(), all_products, getFragmentManager(), subcategory, category);
        cardsShop.setAdapter(adapter);
        Log.d("tagCardFrag", String.valueOf(subcategory));

        preferences.init(getContext());

        getShopping(getContext());
        return view;
    }

    private void getShopping(Context context) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonArrayRequest objectRequest = new JsonArrayRequest(Request.Method.GET, urlAPI.URL_CAR + preferences.readEmail(), null,
                response -> {
                    try {
                        Log.d("tag1", String.valueOf(response.length()));

                        for (int i = 0; i < response.length(); i++) {
                            JSONObject productsObjt = response.getJSONObject(i);
                            String productName = productsObjt.getString("name").toUpperCase();
                            int productPrice = productsObjt.getInt("price");
                            String finalImg = productsObjt.getString("image");

                            int productId = productsObjt.getInt("id_product");
                            preferences.saveProductId(productId);
                            Product product = new Product();
                            product.setImg(finalImg);
                            product.setPrice(productPrice);
                            product.setTitle(productName);
                            all_products.add(product);
                            adapter.notifyDataSetChanged();
                            preferences.saveOrderProducts(product);
                            preferences.saveOrderProductsPrice(productPrice);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.d("tag1", String.valueOf(e));
                    }
                }, error -> Log.d("tagMA", String.valueOf(error)));
        requestQueue.add(objectRequest);
    }
}