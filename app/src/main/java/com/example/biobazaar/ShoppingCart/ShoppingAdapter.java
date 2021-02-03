package com.example.biobazaar.ShoppingCart;

import android.content.Context;
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

import com.example.biobazaar.Favorites.FavoriteAdapter;
import com.example.biobazaar.Favorites.FavoriteFunction;
import com.example.biobazaar.Filters.Product;
import com.example.biobazaar.R;
import com.example.biobazaar.User.Preferences;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ShoppingAdapter extends RecyclerView.Adapter<ShoppingAdapter.ViewHolder> {

    private final List<Product> all_products;

    private Context context;


    public CardView cardView;
    private String subcategory;
    private String category;
    private FragmentManager fragmentManager;
    ShoppingFunction functions = new ShoppingFunction();
    Preferences preferences = new Preferences();
    public int price;
    public ShoppingAdapter(Context cntxt, List<Product> products, FragmentManager fragmentManager, String subcategory, String category) {
        this.all_products = products;
        this.fragmentManager = fragmentManager;
        this.context = cntxt;
        this.subcategory = subcategory;
        this.category = category;
    }

    @NonNull
    @Override
    public ShoppingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_shopping, parent, false);
        cardView = view.findViewById(R.id.shoppingView);
        return new ShoppingAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShoppingAdapter.ViewHolder holder, int position) {
        holder.nome.setText(all_products.get(position).getTitle());
        String finalPrice = toString().valueOf(all_products.get(position).getPrice());
        holder.price.setText(finalPrice + " €");
        Picasso.get()
                .load(all_products.get(position).getImg())
                .into(holder.imgCard);
        holder.removeBtn.setOnClickListener(
                v -> {
                    functions.removeShopping(context, preferences.getProductId());
                    FragmentTransaction fr1 = fragmentManager.beginTransaction();
                    fr1.replace(R.id.fragment_container, new ShoppingFragment(subcategory, category));
                    fr1.commit();
                });
       price = all_products.get(position).getPrice();
    }

    @Override
    public int getItemCount() {
        return all_products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nome, price;
        ImageView imgCard;
        TextView removeBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nome = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
            imgCard = itemView.findViewById(R.id.imgProduct);
            removeBtn = itemView.findViewById(R.id.removeBtn);
        }
    }
}
