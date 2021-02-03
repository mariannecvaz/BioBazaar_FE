package com.example.biobazaar.User;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.biobazaar.HomeFragment;
import com.example.biobazaar.R;
import com.example.biobazaar.urlAPI;

import org.json.JSONException;
import org.json.JSONObject;


public class LoginFragment extends Fragment {
    EditText email, password;
    Button login;
    final int loginFull = R.id.loginFull;
    Preferences preferences = new Preferences();
    TextView txtOpenRegister;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_login, container, false);

        email = v.findViewById(R.id.txtEmail);
        password = v.findViewById(R.id.txtPassword);

        GFragment gFragment = new GFragment();
        openFragment(gFragment, loginFull);

        login = v.findViewById(R.id.btnLogin);
        login.setOnClickListener(v1 -> {
            login(getContext());
            getFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
        });
        txtOpenRegister = v.findViewById(R.id.txtOpenRegister);
        txtOpenRegister.setOnClickListener(va -> getFragmentManager().beginTransaction().setCustomAnimations(
                R.anim.fade_in_pages,// enter
                R.anim.fade_out).replace(R.id.fragment_container, new RegisterFragment()).commit());
        return v;
    }

    private void openFragment(GFragment gFragment, int loginFull) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(loginFull, gFragment);
        ft.commit();
    }

    private void login(Context context) {
        final String finalPassword = password.getText().toString();
        final String finalEmail = email.getText().toString();

        RequestQueue queue = Volley.newRequestQueue(context);

        if (TextUtils.isEmpty(finalEmail)) {
            email.setError("Por favor introduza um email");
            email.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(finalPassword)) {
            password.setError("Por favor introduza uma palavra passe");
            password.requestFocus();
            return;
        }

        JSONObject body = new JSONObject();
        try {
            body.put("password", finalPassword);
            body.put("email", finalEmail);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, urlAPI.URL_LOGIN, body,
                response -> {
                    Log.d("TAG", "login: " + response);
                    try {
                        preferences.init(getContext());
                        JSONObject message = response.getJSONObject("user");
                        String email = message.getString("email");
                        String password = message.getString("password");
                        String name = message.getString("name");
                        String nif = message.getString("nif");
                        String phone = message.getString("phone");
                        String adress = message.getString("adress");
                        String country = message.getString("country");
                        String city = message.getString("city");
                        String zipCode = message.getString("zipCode");
                        int coins = message.getInt("coins");
                        String companyName = message.getString("companyName");

                        Preferences.saveUserEmail(email);
                        Preferences.saveName(name);
                        Preferences.saveCoins(coins);
                        Preferences.saveAdress(adress);
                        Preferences.saveNif(nif);
                        Preferences.savePhone(phone);
                        Preferences.saveCountry(country);
                        Preferences.saveCity(city);
                        Preferences.saveZipCode(zipCode);
                        Preferences.savePass(password);
                        Preferences.saveCompanyName(companyName);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //Toast.makeText(getContext(), "Bem-Vindo/(a)", Toast.LENGTH_SHORT).show();
                },
                error -> Toast.makeText(getContext(), "Confirme a sua palavra passe e o seu email da conta", Toast.LENGTH_SHORT).show());

        queue.add(jsonObjectRequest);

    }

}