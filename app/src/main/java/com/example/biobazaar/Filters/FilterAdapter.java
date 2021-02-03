package com.example.biobazaar.Filters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.biobazaar.R;

import java.util.List;


public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.ViewHolder> {

    private List<Filter> all_filters;
    private Context context;
    private FragmentManager fragmentManager;

    public CardView cardFilter;

    public FilterAdapter(Context context, List<Filter> filter, FragmentManager fragmentManager) {
        this.all_filters = filter;
        this.context = context;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.catagories_button, parent, false);
        cardFilter = view.findViewById(R.id.cardFilter);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.category.setText(all_filters.get(position).getFilter());
        holder.vv.setOnClickListener(
                v -> {
                    FragmentTransaction fr1 = fragmentManager.beginTransaction().setCustomAnimations(
                            R.anim.slide_in_right,  // enter
                            R.anim.slide_out_left); // exit;
                    fr1.replace(R.id.fragment_container, new CatalogFragment(all_filters.get(position).getNameCategory(), all_filters.get(position).getFilter()));
                    fr1.commit();

                });
    }

    @Override
    public int getItemCount() {
        return all_filters.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView category;
        CardView vv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
           category = itemView.findViewById(R.id.bntFilter);
           vv = itemView.findViewById(R.id.cardFilter);
        }
    }
}
