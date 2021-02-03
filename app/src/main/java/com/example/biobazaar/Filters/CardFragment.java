package com.example.biobazaar.Filters;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.biobazaar.R;
import com.example.biobazaar.User.Preferences;
import com.example.biobazaar.urlAPI;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;

public class CardFragment extends Fragment {
    private final String subcategory;
    private final String category;

    RecyclerView cardsCatalog;

    CardAdapter adapter;
    List<Product> all_products;

    Preferences preferences = new Preferences();

    public CardFragment(String subcategory, String category) {
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
        View view = inflater.inflate(R.layout.fragment_card, container, false);
        all_products = new ArrayList<>();

        cardsCatalog = view.findViewById(R.id.recyclerList);
        cardsCatalog.setLayoutManager(new GridLayoutManager(getContext(), 2));

        adapter = new CardAdapter(getContext(), all_products, getFragmentManager(), subcategory, category);

        cardsCatalog.setAdapter(adapter);
        Log.d("tagCardFrag", String.valueOf(subcategory));
        getProducts(getContext());

        return view;
    }

    private void getProducts(Context context) {
        Log.d("tagCardFrag", String.valueOf(subcategory));
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonArrayRequest objectRequest = new JsonArrayRequest(Request.Method.GET, urlAPI.URL_PRODUCT_SUBCATEGORY + subcategory, null,
                response -> {
                    try {
                        Log.d("tag1", String.valueOf(response.length()));

                        for (int i = 0; i < response.length(); i++) {
                            JSONObject productsObjt = response.getJSONObject(i);
                            String productName = productsObjt.getString("name").toUpperCase();
                            int productPrice = productsObjt.getInt("price");
                            String finalImg = productsObjt.getString("img");
                            int productId = productsObjt.getInt("id_product");
                            preferences.saveProductId(productId);

                            Product product = new Product();
                            product.setImg(finalImg);
                            product.setPrice(productPrice);
                            product.setTitle(productName);
                            all_products.add(product);
                            adapter.notifyDataSetChanged();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.d("tag1", String.valueOf(e));
                    }
                }, error -> Log.d("tagMA", String.valueOf(error)));
        requestQueue.add(objectRequest);
    }

}