package com.example.shopclothes.network;

import com.example.shopclothes.view.activity.account.register.ResponseUser;
import com.example.shopclothes.view.activity.cart.ResponseCart;
import com.example.shopclothes.view.activity.product.response.ResponseComment;
import com.example.shopclothes.view.activity.product.response.ResponseProduct;
import com.example.shopclothes.view.activity.product.response.ResponseSize;
import com.example.shopclothes.view.fragment.homeFragment.response.ResponseBanner;
import com.example.shopclothes.view.fragment.homeFragment.response.ResponseTypeProduct;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
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

    // banner
    @GET(ManagerUrl.READ_BANNER)
    Call<ResponseBanner> readBanner();

    // product
    @GET(ManagerUrl.READ_PRODUCT_NEW)
    Call<ResponseProduct> readProductNew();
    @GET(ManagerUrl.READ_PRODUCT_OUTSTANDING)
    Call<ResponseProduct> readProductOutstanding();
    @GET(ManagerUrl.READ_PRODUCT_ALL)
    Call<ResponseProduct> readProductAll();
    @POST(ManagerUrl.READ_PRODUCT_ID_CATEGORY)
    @FormUrlEncoded
    Call<ResponseProduct> readProductByIdCategory(@Field("id") int id);
    @POST(ManagerUrl.READ_PRODUCT_ID)
    @FormUrlEncoded
    Call<ResponseProduct> readProductById(@Field("id") int id);
    // type product
    @GET(ManagerUrl.READ_TYPE_PRODUCT)
    Call<ResponseTypeProduct> readTypeProduct();
    // comment
    @POST(ManagerUrl.READ_COMMENT_ID)
    @FormUrlEncoded
    Call<ResponseComment> readCommentById(@Field("id") int id);
    // size
    @POST(ManagerUrl.READ_SIZE_ID_PRODUCT)
    @FormUrlEncoded
    Call<ResponseSize> readSizeByIdProduct(@Field("id") int id);
    // cart
    @GET(ManagerUrl.READ_CART)
    Call<ResponseCart> readCart();
    @POST(ManagerUrl.INSERT_CART)
    @FormUrlEncoded
    Call<ResponseCart> insertCart(@Field("quantity") int quantity,
                                  @Field("price") double price,
                                  @Field("size") String size,
                                  @Field("userId") String userId,
                                  @Field("productId") int productId);
}
