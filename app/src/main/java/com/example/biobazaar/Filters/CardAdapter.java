package com.example.biobazaar.Filters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.biobazaar.Favorites.FavoriteFragment;
import com.example.biobazaar.Favorites.FavoriteFunction;
import com.example.biobazaar.R;
import com.example.biobazaar.ShoppingCart.ShoppingFunction;
import com.example.biobazaar.User.Preferences;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    private List<Product> allProducts;
    private Context context;

    public CardView cardView;

    private String subcategory;
    private String category;

    private FragmentManager fragmentManager;

    FavoriteFunction favoriteFunction;
    ShoppingFunction shoppingfunctions = new ShoppingFunction();

    Preferences preferences = new Preferences();

    public CardAdapter(Context cntxt, List<Product> products, FragmentManager fragmentManager, String subcategory, String category) {
        this.allProducts = products;
        this.fragmentManager = fragmentManager;
        this.context = cntxt;
        this.subcategory = subcategory;
        this.category = category;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_product, parent, false);

        cardView = view.findViewById(R.id.cardView);
        favoriteFunction = new FavoriteFunction(fragmentManager);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.nome.setText(allProducts.get(position).getTitle());
        String finalPrice = toString().valueOf(allProducts.get(position).getPrice());
        holder.price.setText(finalPrice + " €");
        Picasso.get()
                .load(allProducts.get(position).getImg())
                .into(holder.imgCard);

        holder.vv.setOnClickListener(
                v -> {
                    Log.d("TAG", "onBindViewHolder: hey hey");

                    FragmentTransaction fr1 = fragmentManager.beginTransaction();
                    fr1.replace(R.id.fragment_container, new ProductFragment(subcategory, category, preferences.getProductId(), fragmentManager));
                    fr1.commit();

                });

        AtomicInteger likeCount = new AtomicInteger();
        Log.d("TAG", "onBindViewHolder: " + favoriteFunction.getFavorites(context, allProducts.get(position).getTitle()));
        if (favoriteFunction.getFavorites(context, allProducts.get(position).getTitle()) == 1) {
            holder.btnLike.setImageResource(R.drawable.ic_heart);
            likeCount.set(1);
        } else {
            holder.btnLike.setImageResource(R.drawable.ic_favorito);
            likeCount.set(0);
        }
        holder.btnLike.setOnClickListener(
                v -> {
                    if (likeCount.get() == 0) {
                        holder.btnLike.setImageResource(R.drawable.ic_heart);
                        favoriteFunction.saveFavorites(context, preferences.getProductId());
                        likeCount.set(1);
                    } else {
                        holder.btnLike.setImageResource(R.drawable.ic_favorito);
                        favoriteFunction.removeFavorites(context, preferences.getProductId());
                        likeCount.set(0);
                    }
                });

        AtomicInteger favoriteCount = new AtomicInteger();

        holder.btnBag.setOnClickListener(
                v -> {
                    if (favoriteCount.get() == 0) {
                        holder.btnBag.setImageResource(R.drawable.ic_carrinhocor);
                        shoppingfunctions.saveShopping(context, preferences.getProductId());
                        favoriteCount.set(1);
                    } else {
                        holder.btnBag.setImageResource(R.drawable.ic_carrinho);
                        shoppingfunctions.removeShopping(context, preferences.getProductId());
                        favoriteCount.set(0);
                    }
                });

    }

    @Override
    public int getItemCount() {
        return allProducts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nome, price;
        ImageView imgCard;

        CardView vv;
        ImageView btnLike, btnBag;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nome = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
            imgCard = itemView.findViewById(R.id.imgProduct);
            vv = itemView.findViewById(R.id.cardView);
            btnLike = itemView.findViewById(R.id.btnLike);
            btnBag = itemView.findViewById(R.id.btnBag);

        }
    }
}

