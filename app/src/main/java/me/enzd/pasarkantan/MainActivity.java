package me.enzd.pasarkantan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import me.enzd.pasarkantan.apiclientproduct.ProductClient;
import me.enzd.pasarkantan.apiclientproduct.ProductInterface;
import me.enzd.pasarkantan.apiclientproduct.Products;
import me.enzd.pasarkantan.productadapter.ProductAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    private long backPressedTime;
    private Toast backToast;
    SharedPreferences sharedPref;
    ProductInterface productInterface;
    RecyclerView rec_main;
    ProductAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rec_main = findViewById(R.id.rec_main);
        rec_main.setLayoutManager(new LinearLayoutManager(this));

        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String namauser = sharedPref.getString(Masuk.KEY_USER, null);

        productInterface = ProductClient.getProduct().create(ProductInterface.class);

        Toast.makeText(this, "Anda login sebagai: "+namauser, Toast.LENGTH_SHORT).show();

        getAllProduct();
    }

    private void getAllProduct() {
        Call<List<Products>> getProduct = productInterface.getProduct();
        getProduct.enqueue(new Callback<List<Products>>() {
            @Override
            public void onResponse(Call<List<Products>> call, Response<List<Products>> response) {
                ArrayList<Products> listProduct =(ArrayList<Products>)response.body();
                Log.d("list_product: ", response.raw().toString());
                Log.d("list_product: ", listProduct.toString());

                adapter = new ProductAdapter(listProduct);
                rec_main.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Products>> call, Throwable t) {
                Log.d("list_product: ", t.getMessage());
            }
        });
    }


    @Override
    // agar klik 2 kali untuk keluar
    public void onBackPressed() {

        if(backPressedTime + 2000 > System.currentTimeMillis()){
            backToast.cancel();
            super.onBackPressed();
            return;
        }else{
            backToast = Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT);
            backToast.show();
        }

        backPressedTime = System.currentTimeMillis();
    }

    // logout
    public void logout(View view) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.remove(Masuk.KEY_USER);
        editor.apply();
        finish();
    }

    // kehalaman checkout
    public void checkout(View view){
        Intent it = new Intent(this, Checkout.class);
        startActivity(it);
    }

}