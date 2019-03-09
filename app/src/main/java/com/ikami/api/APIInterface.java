package com.ikami.api;

import com.ikami.pojo.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by mfuon on 20/01/19.
 */

public interface APIInterface {


    @GET("check-if-admin-exist?")
    Call<User> checkIfExist(@Query("username") String username,@Query("key") String key,  @Query("password") String password);

    @POST("verify-qr-code?")
    Call<User> verifyQrMainGate(@Query("key") String key, @Query("national_id") String national_id,  @Query("password") String password,@Query("activity_type") String activity_type);

    @POST("verify-qr-code?")
    Call<User> postStatistics(@Query("key") String key, @Query("national_id") String national_id, @Query("password") String password, @Query("activity_type") String activity_type, @Query("sector") String sector);
}
