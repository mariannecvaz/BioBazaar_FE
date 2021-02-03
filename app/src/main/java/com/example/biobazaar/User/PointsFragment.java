package com.example.biobazaar.User;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.biobazaar.R;

public class PointsFragment extends Fragment {
    Preferences preferences = new Preferences();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_points, container, false);

        ImageButton backBtn = view.findViewById(R.id.btnBack);
        backBtn.setOnClickListener(
                v -> {
                    FragmentTransaction fr1 = getFragmentManager().beginTransaction().setCustomAnimations(
                            R.anim.fade_in_pages,  // enter
                            R.anim.fade_out);
                    fr1.replace(R.id.fragment_container, new LoggedFragment());
                    fr1.commit();
                });


        preferences.init(getContext());
        TextView username = view.findViewById(R.id.username);
        username.setText(preferences.readUserName());

        TextView coins = view.findViewById(R.id.txtCoins);
        coins.setText("Tem " + preferences.getCoins() + " pontos acumulados na sua Conta!");



        ImageButton infoBtn = view.findViewById(R.id.infoBtn);
        infoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonShowPopupWindowClick(v);
            }
        });
        return view;
    }

    private void setContentView(int activity_main) {
    }

    public void onButtonShowPopupWindowClick(View view) {

        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_window, null);

        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        // dismiss the popup window when touched
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
    }
}

