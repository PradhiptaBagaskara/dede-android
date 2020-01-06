package com.ruvio.reawrite.network;

import com.ruvio.reawrite.home.api.ResponseGetCerita;
import com.ruvio.reawrite.login.api.ResponseUser;
import com.ruvio.reawrite.profile.api.ResponseProfile;
import com.ruvio.reawrite.profile.api.ResponseProfile;
import com.ruvio.reawrite.tulis.ResponseTulis;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiServices {

    @GET("api/cerita")
    Call<ResponseGetCerita> getAllCerita();

    @Multipart
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
    Call<ResponseNull> postMasukan(@Field("masukan") String masukan);

    @Multipart
    @POST("api/profil")
    Call<ResponseProfile> postProfile(@Part("auth_key") RequestBody auth_key,
                                    @Part("nama") RequestBody nama,
                                    @Part("username") RequestBody username,
                                    @Part("email") RequestBody email,
                                    @Part("password") RequestBody password,
                                    @Part MultipartBody.Part image);

    @Multipart
    @POST("api/profil")
    Call<ResponseProfile> postProfileNoImg(@Part("auth_key") RequestBody auth_key,
                                      @Part("nama") RequestBody nama,
                                      @Part("username") RequestBody username,
                                      @Part("email") RequestBody email,
                                      @Part("password") RequestBody password);
    @GET("api/profil")
    Call<ResponseProfile> getProfile(@Query("auth_key") String auth_key);

}
