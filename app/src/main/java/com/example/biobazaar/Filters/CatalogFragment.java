package com.example.biobazaar.Filters;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.biobazaar.R;

public class CatalogFragment extends Fragment {

    final int catalog = R.id.catalogFull;
    private String category;
    private String subcategory;

    public CatalogFragment(String category, String subcategory) {
        this.category = category;
        this.subcategory = subcategory;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_catalog, container, false);

        final int recyclerContainer = R.id.frameLayoutCatalog;
        TextView txtSub = view.findViewById(R.id.subcategoryTxt);
        txtSub.setText(subcategory.toUpperCase());

        TextView txtCat = view.findViewById(R.id.categoryTxt);
        txtCat.setText(category.toUpperCase());

        CardFragment card = new CardFragment(subcategory, category);
        openFragment(card, recyclerContainer);

        ImageButton backBtn = view.findViewById(R.id.backBtn);
        backBtn.setOnClickListener(
                v -> {
                    FragmentTransaction fr1 = getFragmentManager().beginTransaction().setCustomAnimations(
                            R.anim.slide_in_left,  // enter
                            R.anim.slide_out_right);
                    fr1.replace(R.id.fragment_container, new SubCategoriesFragment(category));
                    fr1.commit();
                });

        return view;
    }

    private void openFragment(CardFragment card, int catalog) {
        FragmentTransaction fr1 = getFragmentManager().beginTransaction();
        fr1.replace(catalog, card);
        fr1.commit();

    }


}