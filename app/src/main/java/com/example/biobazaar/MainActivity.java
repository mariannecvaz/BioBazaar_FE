package com.example.biobazaar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.biobazaar.Favorites.FavoriteFragment;
import com.example.biobazaar.Filters.CategoriesFragment;
import com.example.biobazaar.ShoppingCart.ShoppingFragment;
import com.example.biobazaar.User.LoggedFragment;
import com.example.biobazaar.User.LoginFragment;
import com.example.biobazaar.User.Preferences;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    Preferences preferences = new Preferences();

    private String subcategory;
    private String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.home);

        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
        preferences.init(getApplicationContext());
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment selectFragment = null;
            switch (menuItem.getItemId()) {
                case R.id.home:
                    selectFragment = new HomeFragment();
                    break;

                case R.id.search:
                        selectFragment = new CategoriesFragment();
                    break;

                case R.id.favorite:
                        selectFragment = new FavoriteFragment(subcategory, category);
                    break;

                case R.id.shopping:
                    selectFragment = new ShoppingFragment(subcategory, category);
                    break;

                case R.id.profile:

                    if (preferences.readUserName() != null) {
                        selectFragment = new LoggedFragment();
                    } else {
                        selectFragment = new LoginFragment();
                    }
                    break;
            }

            getSupportFragmentManager().beginTransaction().setCustomAnimations(
                    R.anim.slide_in_right,// enter
                    R.anim.slide_out_left).replace(R.id.fragment_container, selectFragment).commit();
            return true;
        }
    };
}