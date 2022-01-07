package me.enzd.pasarkantan;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Masuk extends AppCompatActivity {
    private EditText edtUsernameL, edtPassL;
    private String username, password;
    Button btnMasuk;
    private String URL = "http://10.0.2.2/API/login_android/login.php";
    static String KEY_USER = "username";
    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_masuk);

        username = password = "";

        edtUsernameL = findViewById(R.id.edtUsernameL);
        edtPassL = findViewById(R.id.edtPassL);
        btnMasuk = findViewById(R.id.btnMasuk);
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

        String namaUser = sharedPref.getString(Masuk.KEY_USER, null);
        if(namaUser != null){
            startActivity(new Intent(this, MainActivity.class));
        }
    }

    public void login(View v){
        username = edtUsernameL.getText().toString().trim();
        password = edtPassL.getText().toString().trim();

        // menyimpan username
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(KEY_USER, username);
        editor.commit();

        // cek username dan password valid atau tidak
        if(!username.equals("") && !password.equals("")){
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.equals("success")) {
                        Intent it = new Intent(Masuk.this, MainActivity.class);
                        startActivity(it);
                        finish();
                    } else if(response.equals("failure")){
                        Toast.makeText(Masuk.this, "Invalid Login Username/Password", Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Masuk.this, error.toString().trim(), Toast.LENGTH_SHORT).show();
                }
            }){
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> data = new HashMap<>();
                    data.put("username", username);
                    data.put("password", password);
                    return data;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
        }else{
            Toast.makeText(this, "Fields can not be empty!", Toast.LENGTH_SHORT).show();
        }
    }

    public void daftar(View view) {
        Intent it = new Intent(this, Daftar.class);
        startActivity(it);
        finish();
    }
}