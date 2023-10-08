package com.example.shopclothes.network;

import com.example.shopclothes.view.activity.account.register.ResponseUser;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {
    ApiService API_SERVICE = new Retrofit.Builder()
            .baseUrl(ManagerUrl.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService.class);

    // user
    @POST(ManagerUrl.INSERT_USER)
    @FormUrlEncoded
    Call<ResponseUser> insertUser (@Field("id") String id);

}
