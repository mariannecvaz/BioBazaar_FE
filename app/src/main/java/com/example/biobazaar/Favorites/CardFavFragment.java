package com.example.biobazaar.Favorites;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
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
import com.example.biobazaar.Filters.CardAdapter;
import com.example.biobazaar.Filters.Product;
import com.example.biobazaar.R;
import com.example.biobazaar.User.Preferences;
import com.example.biobazaar.urlAPI;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CardFavFragment extends Fragment {
    public final String subcategory;
    public final String category;

    RecyclerView cardsFav;


    List<Product> all_products = new ArrayList<>();

    FavoriteAdapter adapterC;
    Preferences preferences = new Preferences();

    public CardFavFragment(String subcategory, String category) {
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
        View view = inflater.inflate(R.layout.fragment_card_fav, container, false);


        cardsFav = view.findViewById(R.id.recyclerFavoriteList);
        cardsFav.setLayoutManager(new LinearLayoutManager(getContext()));


        adapterC = new FavoriteAdapter(getContext(), all_products, getFragmentManager(), subcategory, category);
        cardsFav.setAdapter(adapterC);
        getFavorites(getContext());
        preferences.init(getContext());

        return view;
    }

    private void getFavorites(Context context) {

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonArrayRequest objectRequest = new JsonArrayRequest(Request.Method.GET, urlAPI.URL_FAV + preferences.readEmail(), null,
                response -> {
                    try {
                        Log.d("tag1STGH", String.valueOf(response.length()));

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
                            adapterC.notifyDataSetChanged();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.d("tag1", String.valueOf(e));
                    }
                }, error -> Log.d("tagMA", String.valueOf(error)));
        requestQueue.add(objectRequest);
    }

}