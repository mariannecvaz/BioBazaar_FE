package com.example.biobazaar.User;

import android.content.Context;
import android.content.Intent;
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
import com.example.biobazaar.Filters.CategoriesFragment;
import com.example.biobazaar.MainActivity;
import com.example.biobazaar.R;
import com.example.biobazaar.urlAPI;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterFragment extends Fragment {

    TextView txtOpenLogin;
    EditText email, password, confirmPassword, username, name;
    Button register;
    final int registerFull = R.id.registerFull;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        GFragment gFragment = new GFragment();
        /*openFragment(gFragment, registerFull);*/

        email = view.findViewById(R.id.txtEmail);
        password = view.findViewById(R.id.txtPassword);
        confirmPassword = view.findViewById(R.id.txtConfPassword);
        name = view.findViewById(R.id.txtName);
        register = view.findViewById(R.id.btnRegister);
        txtOpenLogin = view.findViewById(R.id.txtOpenLogin);

        register.setOnClickListener(v -> {
            register(getContext());
            FragmentTransaction fr1 = getFragmentManager().beginTransaction();
            fr1.replace(R.id.fragment_container, new LoginFragment());
            fr1.commit();
        });
        txtOpenLogin.setOnClickListener(v ->  getFragmentManager().beginTransaction().replace(R.id.fragment_container, new LoginFragment()).commit());
        return view;
    }

    private void openFragment(GFragment gFragment, int registerFull) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(registerFull, gFragment);
        ft.commit();
    }

    private void register(Context context) {
        final String finalName = name.getText().toString();
        final String finalEmail = email.getText().toString();
        final String finalPassword = password.getText().toString();
        final String finalConfPassword = confirmPassword.getText().toString();
        RequestQueue queue = Volley.newRequestQueue(getContext());
        if (TextUtils.isEmpty(finalName)) {
            name.setError("Por favor insira o seu Nome");
            name.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(finalEmail)) {
            email.setError("Por favor insira o seu Email");
            email.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(finalPassword)) {
            password.setError("Por favor insira a sua Palavra-Chave");
            password.requestFocus();
            return;
        }

        JSONObject body = new JSONObject();
        try {
            body.put("password", finalPassword);
            body.put("name", finalName);
            body.put("email", finalEmail);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, urlAPI.URL_REGISTER, body,
                response -> {
                    String message = "";
                    try {
                        message = response.get("res").toString();
                        Log.d("Registo", message);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(getContext(), "Conta criada com sucesso", Toast.LENGTH_SHORT).show();
                },
                error -> Log.d("tag", String.valueOf(error)));

        queue.add(jsonObjectRequest);
    }
}