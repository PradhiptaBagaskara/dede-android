package com.ruvio.reawrite.kategori.api;

import com.ruvio.reawrite.kategori.cerita.ResponseById;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface KategoriServices {

    @GET("api_kategori.php")
    Call<ResponseKategori> getKategoriResponse();

    @POST("order_id_kategori.php")
    Call<ResponseById> postById(@Field("id_kategori") String idKategori,
                                @Field("auth_key") String token);
}
