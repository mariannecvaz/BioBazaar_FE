package com.example.biobazaar.Filters;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.biobazaar.R;
import com.example.biobazaar.urlAPI;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class BtnSubFragment extends Fragment {
    RecyclerView buttonsCatalog;
    String nameCategory;

    FilterAdapter fadapter;
    List<Filter> all_filters;

    public BtnSubFragment(String nameCategory) {
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
        View view = inflater.inflate(R.layout.fragment_button, container, false);
        all_filters = new ArrayList<>();

        buttonsCatalog = view.findViewById(R.id.recyclerButtonList);
        buttonsCatalog.setLayoutManager(new LinearLayoutManager(getContext()));

        fadapter = new FilterAdapter(getContext(), all_filters, getFragmentManager());

        buttonsCatalog.setAdapter(fadapter);

        getSubCategories(getContext());

        return view;
    }



    private void getSubCategories(Context context) {
        Log.d("TAG", "getSubCategories: " + nameCategory);
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonArrayRequest objectRequest = new JsonArrayRequest(Request.Method.GET, urlAPI.URL_SUBCATEGORY + nameCategory, null,
                response -> {
                    try {
                        for (int i = 0; i < response.length(); i++) {
                            String subcategory = response.getString(i);

                            Filter filter = new Filter();
                            filter.setNameCategory(nameCategory);
                            filter.setFilter(subcategory);

                            all_filters.add(filter);
                            Log.d("TAG", " BVCXZDCFGHJK:" + subcategory);
                            fadapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.d("tag1", String.valueOf(e));
                    }
                }, error -> Log.d("tag", String.valueOf(error)));
        requestQueue.add(objectRequest);
    }
}