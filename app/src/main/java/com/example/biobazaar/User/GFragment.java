package com.example.biobazaar.User;

import android.content.AsyncQueryHandler;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpResponse;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.biobazaar.R;
import com.example.biobazaar.urlAPI;
import com.google.android.gms.auth.api.credentials.Credentials;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.prefs.Preferences;


public class GFragment extends Fragment {
    GoogleSignInAccount account;
    GoogleSignInClient mGoogleSignInClient;
    GoogleSignInOptions googleSignInOptions;
    private String TAG = "tagGoogle";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(urlAPI.idGoogle)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), googleSignInOptions);
        account = GoogleSignIn.getLastSignedInAccount(getContext());
    }

    int SIGN = 9002;

    String idToken;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_google, container, false);
        SignInButton google = view.findViewById(R.id.btnGoogle);
        mGoogleSignInClient = GoogleSignIn.getClient(getContext(), googleSignInOptions);

        google.setOnClickListener(v -> {
            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, SIGN);
        });
        return view;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, String.valueOf(requestCode));
        if (requestCode == SIGN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        } else {
            onActivityResult(requestCode, resultCode, data);
        }

    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            String token = account.getIdToken();
            googleAuth(token, getContext());
            Log.d(TAG, String.valueOf(account));
        } catch (ApiException e) {
            Log.d(TAG, String.valueOf(e));
        }
    }

    public static void googleAuth(String bearer, Context context) {
        RequestQueue queue = Volley.newRequestQueue(context);
        Log.d("tag", bearer);

        JSONObject postData = new JSONObject();
        try {
            postData.put("token", bearer);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, urlAPI.URL_GOOGLE, postData,
                response -> {
                    try {
                        String token = response.get("token").toString();
                        Log.d("fds", "googleAuth: "+token);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Toast toast = Toast.makeText(context, "Bem Vindo/(a)", Toast.LENGTH_SHORT);
                    toast.show();
                    //RETIREI STATIC NO PUBLIC VOID POR CAUSA DO TOAST!
                    /*getFragmentManager().beginTransaction().replace(R.id.loginFull, new LoggedFragment()).commit();*/
                },
                error -> Log.d("tag", String.valueOf(error)));
        queue.add(jsonObjectRequest);
    }
}