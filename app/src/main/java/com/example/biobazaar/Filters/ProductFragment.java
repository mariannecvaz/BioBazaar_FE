package com.example.biobazaar.Filters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.biobazaar.Favorites.FavoriteFunction;
import com.example.biobazaar.Filters.CatalogFragment;
import com.example.biobazaar.R;
import com.example.biobazaar.User.Preferences;
import com.example.biobazaar.urlAPI;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.atomic.AtomicInteger;

public class ProductFragment extends Fragment {

    private final int idProduct;
    private final FragmentManager fragmentManager;
    TextView productCount;
    TextView name, price,description;
    ImageView imgCard;
    RelativeLayout card;
    Button btnSeeMore;
    private String subcategory;
    private String category;
    ImageView likeBtn;
    FavoriteFunction functions;

    Preferences preferences = new Preferences();

    private Context context;

    public ProductFragment(String subcategory, String category, int id_Product, FragmentManager fragmentManager) {
        this.idProduct = id_Product;
        this.subcategory = subcategory;
        this.category = category;
        this.fragmentManager = fragmentManager;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("ResourceType")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product, container, false);
        getProducts(getContext());
        AtomicInteger count = new AtomicInteger();
        card = view.findViewById(R.id.card);
        name = view.findViewById(R.id.name);
        price = view.findViewById(R.id.price);
        imgCard = view.findViewById(R.id.image);
        description = view.findViewById(R.id.description);
        functions = new FavoriteFunction(fragmentManager);

        TextView txtsub = view.findViewById(R.id.textView);
        txtsub.setText(subcategory.toUpperCase());

        likeBtn = view.findViewById(R.id.likeButton);

        likeBtn.setOnClickListener(
                v -> {
                    if (count.get() == 0) {
                        likeBtn.setImageResource(R.drawable.ic_heart);
                        functions.saveFavorites(getContext(), preferences.getProductId());
                        count.set(1);
                    } else {
                        likeBtn.setImageResource(R.drawable.ic_favorito);
                        functions.saveFavorites(getContext(), preferences.getProductId());
                        count.set(0);
                    }
                }
        );


        ImageButton backBtn = view.findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fr1 = getFragmentManager().beginTransaction().setCustomAnimations(
                        R.anim.fade_in_pages,// enter
                        R.anim.fade_out);
                fr1.replace(R.id.fragment_container, new CatalogFragment(category, subcategory));
                preferences.removeProductId();
                fr1.commit();
            }
        });


        return view;
    }

    private void getProducts(Context context) {
        Log.d("TAG", "getProducts: " + idProduct);
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, urlAPI.URL_PRODUCT + "/" + idProduct, null,
                response -> {
                    try {
                        Log.d("TAG", "getProducts: " + response);
                        String productName = response.getString("name").toUpperCase();
                        String productPrice = response.getString("price").toUpperCase();

                        String finalImg = response.getString("img");
                        String producsDescription = response.getString("description");
                        Log.d("TAG", "getProducts: " + productName);
                        name.setText(productName);
                        price.setText(productPrice + " €");
                        description.setText(producsDescription);
                        Picasso.get()
                                .load(finalImg)
                                .centerCrop()
                                .resize(412, 450)
                                .into(imgCard);
                        if (functions.getFavorites(getContext(), productName) == 1) {
                            likeBtn.setImageResource(R.drawable.ic_heart);
                        } else {
                            likeBtn.setImageResource(R.drawable.ic_favorito);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }, error -> Log.d("tagMAAA", String.valueOf(error)));
        requestQueue.add(objectRequest);
    }

}