package com.example.biobazaar.User;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.biobazaar.R;
import com.example.biobazaar.urlAPI;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class OrderFunctions {
    Preferences preferences = new Preferences();
    private FragmentManager fragmentManager;

    public void saveOrders(Context context, FragmentManager fragmentManager, JSONObject body) {
        this.fragmentManager = fragmentManager;
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, urlAPI.URL_ORDER + preferences.readEmail(), body, response -> {
            try {
                FragmentTransaction fr1 = fragmentManager.beginTransaction();
                        fr1.replace(R.id.fragment_container, new LoggedFragment());
                fr1.commit();

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
}
