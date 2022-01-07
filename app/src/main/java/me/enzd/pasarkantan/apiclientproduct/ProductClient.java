package me.enzd.pasarkantan.apiclientproduct;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductClient {
    public static String BASE_URL = "http:/10.0.2.2/";
    private static Retrofit retrofit = null;

    public static Retrofit getProduct(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
