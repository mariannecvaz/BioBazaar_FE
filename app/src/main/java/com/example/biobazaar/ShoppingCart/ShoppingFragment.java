package com.example.biobazaar.ShoppingCart;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.biobazaar.Favorites.CardFavFragment;
import com.example.biobazaar.Favorites.NoFavoriteFragment;
import com.example.biobazaar.Filters.Product;
import com.example.biobazaar.R;
import com.example.biobazaar.ResumeFragment;
import com.example.biobazaar.User.Preferences;
import com.example.biobazaar.payFragment;
import com.example.biobazaar.urlAPI;

import org.json.JSONException;
import org.json.JSONObject;

public class ShoppingFragment extends Fragment {
    private String subcategory;
    private String category;
    Preferences preferences = new Preferences();
    ShoppingCardFragment cardFr = new ShoppingCardFragment(subcategory, category);
TextView txtCount;
    public ShoppingFragment(String subcategory, String category) {
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
        View view = inflater.inflate(R.layout.fragment_shopping, container, false);
preferences.init(getContext());
        final int recyclerContainer = R.id.frameLayoutShopping;
        getShopping(getContext(), recyclerContainer);
        txtCount = view.findViewById(R.id.txtCount);
        Button btnResume = view.findViewById(R.id.btnResume);
        btnResume.setOnClickListener( v->{
            FragmentTransaction fr1 = getFragmentManager().beginTransaction().setCustomAnimations(
                    R.anim.slide_in_right,  // enter
                    R.anim.slide_out_left);
            fr1.replace(R.id.fragment_container, new payFragment(subcategory, category));
            fr1.commit();
            Log.d("TAG", "getShopping: " + preferences.retrieveOrderProducts());
        });
        return view;
    }

    private void getShopping(Context context , int recyclerContainer) {

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonArrayRequest objectRequest = new JsonArrayRequest(Request.Method.GET, urlAPI.URL_CAR  + preferences.readEmail(), null,
                response -> {
                    Log.d("tag1", String.valueOf(response.length()));
                    if (response.length() == 0) {
                        txtCount.setText("0 ITENS");
                        FragmentTransaction fr1 = getFragmentManager().beginTransaction();
                        fr1.replace(R.id.fragment_container, new NoShoppingFragment());
                        fr1.commit();
                    } else {
                        txtCount.setText(response.length() +  " ITENS");
                        FragmentTransaction fr1 = getFragmentManager().beginTransaction();
                        fr1.replace(recyclerContainer, cardFr);
                        fr1.commit();
                    }
                }, error -> Log.d("tagMA", String.valueOf(error)));
        requestQueue.add(objectRequest);
    }
}