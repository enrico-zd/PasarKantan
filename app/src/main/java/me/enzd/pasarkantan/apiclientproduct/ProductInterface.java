package me.enzd.pasarkantan.apiclientproduct;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ProductInterface {
    @GET("product/")
    Call<List<Products>> getProduct();

    @FormUrlEncoded
    @POST("product/")
    Call<Products> postCurhat(@Field("nama")String nama, @Field("harga")int harga, @Field("gambar")String gambar);

    @DELETE("product/")
    Call<Products> delCurhat(@Query("id")int id);
}
