package com.example.biobazaar.Favorites;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.biobazaar.Filters.CardAdapter;
import com.example.biobazaar.Filters.Product;
import com.example.biobazaar.R;
import com.example.biobazaar.User.Preferences;
import com.example.biobazaar.urlAPI;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class FavoriteFunction {
    CardAdapter adapter;
    Preferences preferences = new Preferences();
    List<Product> all_products;
    private FragmentManager fragmentManager;


    public FavoriteFunction(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public void removeFavorites(Context context, int productId) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.DELETE, urlAPI.URL_FAV + preferences.readEmail() + "/" + productId, null, response -> {
            try {
                Toast.makeText(context, "Produto removido dos Favoritos", Toast.LENGTH_SHORT).show();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }, error -> Log.d("TAG", "saveFavorites: "+error)) {

            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }

    public void saveFavorites(Context context, int productId) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, urlAPI.URL_FAV + preferences.readEmail() + "/" + productId, null, response -> {
            try {
                Toast.makeText(context, "Produto adicionado aos Favoritos", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, error -> Log.d("TAG", "saveFavorites: "+error)) {

            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }

    public int getFavorites(Context context, String product) {
        AtomicInteger heartStatus = new AtomicInteger();
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonArrayRequest objectRequest = new JsonArrayRequest(Request.Method.GET, urlAPI.URL_FAV + preferences.readEmail(), null,
                response -> {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject productsObjt = null;
                        try {
                            productsObjt = response.getJSONObject(i);
                            String productName = productsObjt.getString("name");
                            Log.d("TAG", "getFavorites: " + response.length()+productName + product.toLowerCase());
                            if (response.length() != 0 && productName == product) {
                                heartStatus.set(1);
                                Log.d("TAGPRIMEIROIF", "getFavorites: "+ heartStatus);
                            } if(response.length() == 0 && productName != product) {
                                heartStatus.set(0);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, error -> Log.d("tagMA", String.valueOf(error)));
        requestQueue.add(objectRequest);
        return heartStatus.get();
    }
}
