package me.enzd.pasarkantan;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

public class Daftar extends AppCompatActivity {
    private TextView cekStatus;
    private EditText edtUsernameD, edtEmailD, edtPassD, edtRePassD, edtPhoneD;
    private Button btnDaftar;
    private String URL = "http://10.0.2.2/API/login_android/register.php";
    private String username, email, no_telp, password, rePass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar);
        cekStatus = findViewById(R.id.cekStatus);
        edtUsernameD =findViewById(R.id.edtUsernameD);
        edtEmailD = findViewById(R.id.edtEmailD);
        edtPassD = findViewById(R.id.edtPassD);
        edtRePassD = findViewById(R.id.edtRePassD);
        edtPhoneD = findViewById(R.id.edtPhoneD);
        btnDaftar = findViewById((R.id.btnDaftar));
        username = email = no_telp = password = rePass = "";

    }

    public void save(View view) {
        username = edtUsernameD.getText().toString().trim();
        email = edtEmailD.getText().toString().trim();
        no_telp = edtPhoneD.getText().toString().trim();
        password = edtPassD.getText().toString().trim();
        rePass = edtRePassD.getText().toString().trim();
        if(!password.equals(rePass)){
            Toast.makeText(this, "Password tidak cocok", Toast.LENGTH_SHORT).show();
        }
        else if(!username.equals("") && !email.equals("") && !no_telp.equals("") && !password.equals("")){
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.equals("success")) {
                        cekStatus.setText("Registrasi Berhasil");
                        btnDaftar.setClickable(false);
                    } else if(response.equals("failure")){
                        cekStatus.setText("Gagal Registrasi");
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), error.toString().trim(), Toast.LENGTH_SHORT).show();
                }
            }){
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> data = new HashMap<>();
                    data.put("username", username);
                    data.put("email", email);
                    data.put("no_telp", no_telp);
                    data.put("password", password);
                    return data;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
        }
    }

    public void login(View view) {
        Intent it = new Intent(this, Masuk.class);
        startActivity(it);
        finish();
    }
}