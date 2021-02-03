package com.example.biobazaar.Favorites;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.provider.ContactsContract;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.biobazaar.Filters.CardFragment;
import com.example.biobazaar.Filters.Product;
import com.example.biobazaar.Filters.SubCategoriesFragment;
import com.example.biobazaar.R;
import com.example.biobazaar.User.Preferences;
import com.example.biobazaar.urlAPI;

import org.json.JSONException;
import org.json.JSONObject;

public class FavoriteFragment extends Fragment {
    private String subcategory;
    private String category;
    CardFavFragment cardFr = new CardFavFragment(subcategory, category);
    Preferences preferences = new Preferences();
TextView txtCount;
    public FavoriteFragment(String subcategory, String category) {
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
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        preferences.init(getContext());
        txtCount = view.findViewById(R.id.txtCount);
        /* cardFr.getFavorites(getContext());*/
        getFavorites(getContext());
        return view;
    }

    private void openFragment(CardFavFragment card, int favorite) {
        FragmentTransaction fr1 = getFragmentManager().beginTransaction();
        fr1.replace(favorite, card);
        fr1.commit();
    }

    private void getFavorites(Context context) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        final int recyclerContainer = R.id.frameLayoutFavorite;
        JsonArrayRequest objectRequest = new JsonArrayRequest(Request.Method.GET, urlAPI.URL_FAV  + preferences.readEmail(), null,
                response -> {
                    Log.d("tag1STGH", String.valueOf(response.length()));
                    if (response.length() == 0) {
                        txtCount.setText("0 ITENS");
                        FragmentTransaction fr1 = getFragmentManager().beginTransaction();
                        fr1.replace(R.id.fragment_container, new NoFavoriteFragment());
                        fr1.commit();
                    } else {
                        txtCount.setText(response.length()+" ITENS");
                        openFragment(cardFr, recyclerContainer);
                    }
                }, error -> Log.d("tagMA", String.valueOf(error)));
        requestQueue.add(objectRequest);
    }
}