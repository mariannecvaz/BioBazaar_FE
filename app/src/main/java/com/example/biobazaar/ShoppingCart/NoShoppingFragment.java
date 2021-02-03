package com.example.biobazaar.ShoppingCart;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.biobazaar.Filters.CategoriesFragment;
import com.example.biobazaar.R;

public class NoShoppingFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_noshopping, container, false);
        Button btnCatalog = view.findViewById(R.id.btnCatalog);
        btnCatalog.setOnClickListener(v -> openFragment());
        return view;
    }

    private void openFragment() {
        FragmentTransaction fr1 = getFragmentManager().beginTransaction();
        fr1.replace(R.id.fragment_container, new CategoriesFragment());
        fr1.commit();
    }
}