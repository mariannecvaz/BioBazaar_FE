package com.example.biobazaar.ShoppingCart;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.biobazaar.Filters.CardAdapter;
import com.example.biobazaar.Filters.Product;
import com.example.biobazaar.User.Preferences;
import com.example.biobazaar.urlAPI;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShoppingFunction {

    Preferences preferences = new Preferences();


    public void removeShopping(Context context, int productId) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.DELETE, urlAPI.URL_CAR + preferences.readEmail() + "/" + productId, null, response -> {
            try {
                Toast.makeText(context, "Produto removido do seu Saco de Compras ", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, error -> Toast.makeText(context, "Erro: " + error, Toast.LENGTH_SHORT).show()) {
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }

    public void saveShopping(Context context, int productId) {

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, urlAPI.URL_CAR + preferences.readEmail() + "/" + productId, null, response -> {
            try {
                Toast.makeText(context, "Produto adicionado ao seu Saco de Compras ", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, error -> Log.d("TAG", "saveShopping: " + error)) {

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

