package com.example.biobazaar.Favorites;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.biobazaar.Filters.CardAdapter;
import com.example.biobazaar.Filters.Product;
import com.example.biobazaar.Filters.ProductFragment;
import com.example.biobazaar.R;
import com.example.biobazaar.ShoppingCart.ShoppingFragment;
import com.example.biobazaar.ShoppingCart.ShoppingFunction;
import com.example.biobazaar.User.Preferences;
import com.example.biobazaar.urlAPI;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {
    private List<Product> all_products;

    private Context context;


    public CardView cardView;

    private String subcategory;
    private String category;

    private FragmentManager fragmentManager;

    FavoriteFunction favoriteFunction;
    ShoppingFunction shoppingFunctions = new ShoppingFunction();

    Preferences preferences = new Preferences();


    public FavoriteAdapter(Context cntxt, List<Product> products, FragmentManager fragmentManager, String subcategory, String category) {
        this.all_products = products;
        this.fragmentManager = fragmentManager;
        this.context = cntxt;
        this.subcategory = subcategory;
        this.category = category;
    }

    @NonNull
    @Override
    public FavoriteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_favorite, parent, false);
        cardView = view.findViewById(R.id.favoriteView);
        favoriteFunction = new FavoriteFunction(fragmentManager);
        return new FavoriteAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteAdapter.ViewHolder holder, int position) {
        AtomicInteger count = new AtomicInteger();

        holder.nome.setText(all_products.get(position).getTitle());
        String finalPrice = toString().valueOf(all_products.get(position).getPrice());
        holder.price.setText(finalPrice + " €");
        Picasso.get()
                .load(all_products.get(position).getImg())
                    .into(holder.imgCard);


        holder.removeBtn.setOnClickListener(
                v -> {
                    favoriteFunction.removeFavorites(context, preferences.getProductId());

                    FragmentTransaction fr1 = fragmentManager.beginTransaction();
                    fr1.replace(R.id.fragment_container, new FavoriteFragment(subcategory, category));
                    fr1.commit();
                });

        holder.btnBag.setOnClickListener(
                v -> {
                    if (count.get() == 0) {
                        holder.btnBag.setImageResource(R.drawable.ic_carrinhocor);
                        shoppingFunctions.saveShopping(context, preferences.getProductId());
                        count.set(1);
                    } else {
                        holder.btnBag.setImageResource(R.drawable.ic_carrinho);
                        shoppingFunctions.removeShopping(context, preferences.getProductId());
                        count.set(0);
                    }
                });

    }

    @Override
    public int getItemCount() {
        return all_products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nome, price;
        ImageView imgCard;

        ImageView btnBag;
        TextView removeBtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nome = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
            imgCard = itemView.findViewById(R.id.imgProduct);

           removeBtn = itemView.findViewById(R.id.removeBtn);
            btnBag = itemView.findViewById(R.id.btnBag);
        }

    }

}
