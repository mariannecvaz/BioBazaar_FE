package com.example.biobazaar.Filters;

import android.content.Context;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.biobazaar.R;
import com.example.biobazaar.urlAPI;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class CategoriesFragment extends Fragment {

    ImageView imgCategory, imgWell,imgHome,imgPacks;
    CardView btnHygiene, btnWellBeing, btnHome, btnPacks;
    SubCategoriesFragment sf;
    String btnId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_categories, container, false);


        sf = new SubCategoriesFragment(btnId);
        getCategories(getContext());
        btnHygiene = view.findViewById(R.id.btnHygiene);
        btnWellBeing = view.findViewById(R.id.btnWellBeing);
        btnPacks = view.findViewById(R.id.btnPacks);
        btnHome = view.findViewById(R.id.btnHome);
        imgCategory = view.findViewById(R.id.imgBtnHygiene);
        imgWell = view.findViewById(R.id.imgWell);
        imgHome = view.findViewById(R.id.imgHome);
        imgPacks = view.findViewById(R.id.imgPacks);
        btnHygiene.setOnClickListener(v -> {
            btnId = "Higiene";
            openFragment();
        });
        btnWellBeing.setOnClickListener(v -> {
            btnId = "Bem-Estar";
            openFragment();

        });
        btnPacks.setOnClickListener(v -> {
            btnId = "Packs e Conjuntos Prenda";
            openFragment();

        });
        btnHome.setOnClickListener(v -> {
            btnId = "Casa";
            openFragment();

        });
        return view;
    }

    private void openFragment() {
        FragmentTransaction fr = getFragmentManager().beginTransaction().setCustomAnimations(
                R.anim.slide_up,  // enter
                R.anim.slide_out_left); // exit;
        fr.replace(R.id.fragment_container, new SubCategoriesFragment(btnId));
        fr.commit();
    }

    private void getCategories(Context context) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonArrayRequest objectRequest = new JsonArrayRequest(Request.Method.GET, urlAPI.URL_CATEGORY, null,
                response -> {
                    try {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject productsObjt = response.getJSONObject(i);
                            String img = productsObjt.getString("img");
                            String name = productsObjt.getString("name");
                            if (name.equals("Higiene")) {
                                Picasso.get()
                                        .load(img)
                                        .centerCrop()
                                        .resize(197, 185)
                                        .into(imgCategory);
                                btnId = name;
                            }

                            if (name.equals("Bem-Estar")) {
                                Picasso.get()
                                        .load(img)
                                        .centerCrop()
                                        .resize(197, 185)
                                        .into(imgWell);
                                btnId = name;
                            }
                            if(name.equals("Packs e Conjuntos Prenda")){
                                Picasso.get()
                                        .load(img)
                                        .centerCrop()
                                        .resize(197, 185)
                                        .into(imgPacks);
                                btnId = name;
                            }
                            if(name.equals("Casa")){
                                Picasso.get()
                                        .load(img)
                                        .centerCrop()
                                        .resize(197, 185)
                                        .into(imgHome);
                                btnId = name;
                            }

                        }
                    } catch (
                            JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> Log.d("tag", String.valueOf(error)));
        requestQueue.add(objectRequest);
    }


}