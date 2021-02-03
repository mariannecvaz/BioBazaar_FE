package com.example.biobazaar.User;

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
import androidx.recyclerview.widget.RecyclerView;

import com.example.biobazaar.User.OrderAdapter;
import com.example.biobazaar.Favorites.FavoriteFunction;
import com.example.biobazaar.Filters.Product;
import com.example.biobazaar.R;
import com.example.biobazaar.ShoppingCart.ShoppingFunction;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    private List<Product> all_products;

    private Context context;

    public CardView cardView;

    private FragmentManager fragmentManager;

    Preferences preferences = new Preferences();

    public OrderAdapter(Context cntxt, List<Product> products, FragmentManager fragmentManager) {
        this.all_products = products;
        this.fragmentManager = fragmentManager;
        this.context = cntxt;
    }

    @NonNull
    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_orders, parent, false);
        cardView = view.findViewById(R.id.orderView);
        return new OrderAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.ViewHolder holder, int position) {
        holder.nome.setText(all_products.get(position).getTitle());
        String finalPrice = toString().valueOf(all_products.get(position).getPrice());
        holder.price.setText(finalPrice + " €");
        Picasso.get()
                .load(all_products.get(position).getImg())
                .into(holder.imgCard);
    }

    @Override
    public int getItemCount() {
        return all_products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nome, price;
        ImageView imgCard;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nome = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
            imgCard = itemView.findViewById(R.id.imgProduct);
        }

    }

}
