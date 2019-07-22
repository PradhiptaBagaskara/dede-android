package com.ruvio.reawrite.kategori.api;

import com.ruvio.reawrite.kategori.cerita.ResponseById;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface KategoriServices {

    @GET("api/kategori")
    Call<ResponseKategori> getKategoriResponse();

    @FormUrlEncoded
    @POST("api/order_by_kategori")
    Call<ResponseById> postById(@Field("id_kategori") String idKategori,
                                @Field("auth_key") String token);
}
