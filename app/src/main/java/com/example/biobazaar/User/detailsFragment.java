package com.example.biobazaar.User;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.biobazaar.R;
import com.example.biobazaar.User.LoggedFragment;
import com.example.biobazaar.User.Preferences;
import com.example.biobazaar.urlAPI;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class detailsFragment extends Fragment {

    Preferences preferences = new Preferences();
    EditText name, password, newPass, confNewPass, companyName, country, adress, zipcode, city, phone, nif;
    TextView email;
    Button btnEdit;
    ImageButton btnBack;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        btnBack = view.findViewById(R.id.btnBack);
        btnBack.setOnClickListener(
                v -> {
                    FragmentTransaction fr1 = getFragmentManager().beginTransaction().setCustomAnimations(
                            R.anim.slide_in_left,  // enter
                            R.anim.slide_out_right);
                    fr1.replace(R.id.fragment_container, new LoggedFragment());
                    fr1.commit();
                }
        );
        btnEdit = view.findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(
                v -> {
                    editProfile(getContext());
                }
        );

        preferences.init(getContext());
        TextView username = view.findViewById(R.id.username);
        username.setText(preferences.readUserName());
        name = view.findViewById(R.id.firstName);
        name.setText(preferences.readUserName());

        email = view.findViewById(R.id.email);
        email.setText(preferences.readEmail());

        password = view.findViewById(R.id.currentPass);
        newPass = view.findViewById(R.id.newPass);
        confNewPass = view.findViewById(R.id.confNewPass);

        companyName = view.findViewById(R.id.companyName);
        companyName.setText(preferences.getCompanyName());

        country = view.findViewById(R.id.country);
        country.setText(preferences.getCountry());

        adress = view.findViewById(R.id.adress);
        adress.setText(preferences.getAdress());

        zipcode = view.findViewById(R.id.postalCode);
        zipcode.setText(preferences.getZipcode());

        city = view.findViewById(R.id.city);
        city.setText(preferences.getCity());

        phone = view.findViewById(R.id.phone);
        phone.setText(preferences.getPhone());

        nif = view.findViewById(R.id.nif);
        nif.setText(preferences.getNif());

        return view;
    }

    private void editProfile(Context context) {
        final String finalName = name.getText().toString();
        final String finalEmail = email.getText().toString();
        final String oldPassword = password.getText().toString();
        final String newPassword = newPass.getText().toString();
        final String finalNif = nif.getText().toString();
        final String finalPhone = phone.getText().toString();
        final String finalCompanyName = companyName.getText().toString();
        final String finalCountry = country.getText().toString();
        final String finalAdress = adress.getText().toString();
        final String finalZipCode = zipcode.getText().toString();
        final String finalCity = city.getText().toString();

        RequestQueue queue = Volley.newRequestQueue(context);

      /*  if (newPassword != newPasswordConf) {
            password.setError("Please enter your Password");
            password.requestFocus();
            return;
        }*/
        JSONObject body = new JSONObject();

        try {
            body.put("passwordNew", newPassword);
            body.put("name", finalName);
            body.put("email", finalEmail);
            body.put("coins", preferences.getCoins());
            body.put("companyName", finalCompanyName);
            body.put("country", finalCountry);
            body.put("adress", finalAdress);
            body.put("zipCode", finalZipCode);
            body.put("city", finalCity);
            body.put("nif", finalNif);
            body.put("phone", finalPhone);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, urlAPI.URL_USER_EDIT + preferences.readEmail(), body,
                response -> {
                    preferences.Logout();
                    Log.d("TAG", "editProfile:" + response);
                    try {
                        String email = response.getString("email");
                        String password = response.getString("password");
                        String adress = response.getString("adress");
                        String zipCode = response.getString("zipCode");
                        String country = response.getString("country");
                        String city = response.getString("city");
                        String name = response.getString("name");
                        String companyName = response.getString("companyName");
                        String nif = response.getString("nif");
                        String phone = response.getString("phone");
                        int coins = response.getInt("coins");

                        preferences.saveName(name);
                        preferences.saveUserEmail(email);
                        preferences.saveCoins(coins);
                        preferences.saveAdress(adress);
                        preferences.saveZipCode(zipCode);
                        preferences.saveCountry(country);
                        preferences.saveCity(city);
                        preferences.saveNif(nif);
                        preferences.savePhone(phone);
                        preferences.saveCompanyName(companyName);
                        preferences.savePass(password);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    getFragmentManager().beginTransaction().replace(R.id.fragment_container, new LoggedFragment()).commit();
                    Toast.makeText(getContext(), "Detalhes da Conta Atualizados!", Toast.LENGTH_SHORT).show();
                },
                error -> Toast.makeText(context, "Erro: " + error, Toast.LENGTH_SHORT).show()) {
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };

        queue.add(jsonObjectRequest);

    }
}