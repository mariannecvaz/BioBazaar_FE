package com.example.biobazaar.User;

import android.content.Context;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.biobazaar.Favorites.CardFavFragment;
import com.example.biobazaar.Favorites.NoFavoriteFragment;
import com.example.biobazaar.R;
import com.example.biobazaar.urlAPI;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

public class OrdersFragment extends Fragment {

    CardOrderFragment cardFr = new CardOrderFragment();
    Preferences preferences = new Preferences();
    TextView noOrder;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_orders, container, false);
        preferences.init(getContext());

        noOrder = view.findViewById(R.id.noOrder);

        ImageButton backBtn = view.findViewById(R.id.btnBack);
        backBtn.setOnClickListener(
                v -> {
                    FragmentTransaction fr1 = getFragmentManager().beginTransaction().setCustomAnimations(
                            R.anim.fade_in_pages,  // enter
                            R.anim.fade_out);
                    fr1.replace(R.id.fragment_container, new LoggedFragment());
                    fr1.commit();
                });
        TextView username = view.findViewById(R.id.username);
        username.setText(preferences.readUserName());
        getOrders(getContext());
        return view;
    }

    public void getOrders(Context context) {
        final int recyclerContainer = R.id.frameLayoutOrder;
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonArrayRequest objectRequest = new JsonArrayRequest(Request.Method.GET, urlAPI.URL_ORDER + preferences.readEmail(), null,
                response -> {
                    if (response.length() == 0) {
                        noOrder.setAlpha(1);

                    } else {
                        noOrder.setAlpha(0);

                        FragmentTransaction fr1 = getFragmentManager().beginTransaction();
                        fr1.replace(recyclerContainer, cardFr);
                        fr1.commit();
                    }

                }, error -> Log.d("tagMAORDER", String.valueOf(error)));
        requestQueue.add(objectRequest);
    }
}