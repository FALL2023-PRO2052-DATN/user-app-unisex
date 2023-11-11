package com.example.shopclothes.network;



import com.example.shopclothes.constant.AppConstants;
import com.example.shopclothes.view.activity.order.response.ResponseModel;
import com.example.shopclothes.view.activity.order.response.ResponsePayment;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiServiceStripe {
    ApiServiceStripe API_SERVICE_STRIPE = new Retrofit.Builder()
            .baseUrl(ManagerUrl.BASE_URL_STRIPE)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiServiceStripe.class);


    @Headers(
            "Authorization: Bearer " + AppConstants.SECRET_KEY
    )
    @POST(ManagerUrl.CUSTOMER)
    Call<ResponseModel> getCustomer();

    @Headers({
            "Authorization: Bearer " + AppConstants.SECRET_KEY,
            "Stripe-Version: 2023-10-16"
    })
    @POST(ManagerUrl.EPHEMERAL_KEY)
    @FormUrlEncoded
    Call<ResponseModel> getEphemeralKey(@Field("customer") String customer);

    @Headers(
            "Authorization: Bearer " + AppConstants.SECRET_KEY
    )
    @FormUrlEncoded
    @POST(ManagerUrl.PAYMENT_INTENTS)
    Call<ResponsePayment> getPaymentIntent(@Field("customer") String customer,
                                           @Field("amount") int amount,
                                           @Field("currency") String currency,
                                           @Field("payment_method_types[]") String card,
                                           @Field("payment_method_types[]") String alipay);
}
