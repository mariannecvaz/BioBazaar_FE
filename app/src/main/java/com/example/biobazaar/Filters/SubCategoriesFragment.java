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

public class SubCategoriesFragment extends Fragment {
    String nameCategory;

    public SubCategoriesFragment(String nameCategory) {
        this.nameCategory = nameCategory;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        final int cardsCatalog = R.id.frameLayoutSearch;
        BtnSubFragment button = new BtnSubFragment(nameCategory);
        openFragment(button, cardsCatalog);

        ImageButton backBtn = view.findViewById(R.id.backBtn);

        backBtn.setOnClickListener(v -> {
            FragmentTransaction fr1 = getFragmentManager().beginTransaction().setCustomAnimations(
                    R.anim.slide_in_left,  // enter
                    R.anim.slide_out_right);
            fr1.replace(R.id.fragment_container, new CategoriesFragment());
            fr1.commit();
        });

        TextView txtCategory = view.findViewById(R.id.txtCategory);
        if (nameCategory == "Higiene") {
            txtCategory.setText("Higiene e Comética".toUpperCase());
        } else {
               txtCategory.setText(nameCategory.toUpperCase());
        }
        return view;
    }

    private void openFragment(BtnSubFragment button, int category) {
        FragmentTransaction fr1 = getFragmentManager().beginTransaction();
        fr1.replace(category, button);
        fr1.commit();
    }
}