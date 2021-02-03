package com.example.biobazaar.User;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.biobazaar.R;
import com.example.biobazaar.User.detailsFragment;

public class LoggedFragment extends Fragment {
    TextView username;
    TextView bntOrders, bntPoints, btnDetails, bntOut;
    Preferences preferences = new Preferences();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        preferences.init(getContext());
        View view = inflater.inflate(R.layout.fragment_loggedprofile, container, false);
        username = view.findViewById(R.id.username);
        username.setText(preferences.readUserName());
        bntOrders = view.findViewById(R.id.bntOrders);
        bntOrders.setOnClickListener(
                v -> {
                    FragmentTransaction fr1 = getFragmentManager().beginTransaction();
                    fr1.replace(R.id.fragment_container, new OrdersFragment());
                    fr1.commit();
                });
        bntPoints = view.findViewById(R.id.bntPoints);
        bntPoints.setOnClickListener(
                v -> {
                    FragmentTransaction fr1 = getFragmentManager().beginTransaction();
                    fr1.replace(R.id.fragment_container, new PointsFragment());
                    fr1.commit();
                });
        btnDetails = view.findViewById(R.id.bntDetails);
        btnDetails.setOnClickListener(
                v -> {
                    FragmentTransaction fr1 = getFragmentManager().beginTransaction();
                    fr1.replace(R.id.fragment_container, new detailsFragment());
                    fr1.commit();
                });
        bntOut = view.findViewById(R.id.bntOut);
        bntOut.setOnClickListener(
                v -> {
                    preferences.Logout();
                    FragmentTransaction fr1 = getFragmentManager().beginTransaction().setCustomAnimations(
                            R.anim.slide_in_left,  // enter
                            R.anim.slide_out_right);
                    fr1.replace(R.id.fragment_container, new LoginFragment());
                    fr1.commit();
                });
        return view;
    }
}
