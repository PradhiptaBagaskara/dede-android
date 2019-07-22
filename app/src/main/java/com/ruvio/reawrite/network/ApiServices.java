package com.ruvio.reawrite.network;

import com.ruvio.reawrite.home.api.ResponseGetCerita;
import com.ruvio.reawrite.login.api.ResponseUser;
import com.ruvio.reawrite.tulis.ResponseTulis;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiServices {

    @GET("api/cerita")
    Call<ResponseGetCerita> getAllCerita();

    @Multipart
//    @FormUrlEncoded
    @POST("api/cerita")
    Call<ResponseTulis> postCerita(@Part("auth_key") RequestBody auth_key,
                                    @Part("id_user") RequestBody idUser,
                                   @Part("id_kategori") RequestBody idKategori,
                                   @Part("judul") RequestBody judul,
                                   @Part("diskripsi") RequestBody diskripsi,
                                   @Part("isi") RequestBody isi,
                                   @Part MultipartBody.Part image);

    @FormUrlEncoded
    @POST("api/login")
    Call<ResponseUser> getUser(@Field("email") String email,
                               @Field("password") String Password);

    @FormUrlEncoded
    @POST("api/register")
    Call<ResponseUser> register(@Field("username") String username,
                                @Field("email") String email,
                               @Field("password") String Password);

    @FormUrlEncoded
    @POST("api/masukan")
    Call<ResponseNull> postMasukan(@Field("id_user") String id,
                                @Field("auth_key") String auth,
                                @Field("masukan") String masukan);
}
