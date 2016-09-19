package com.psychscribe.rest;


import com.google.gson.JsonObject;
import com.psychscribe.model.StateModel;
import com.psychscribe.patients.model.PatientModel;
import com.psychscribe.prelogin.model.SignInModel;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Android-134 on 3/29/2016.
 */
public interface ResponseInterface
{
    @FormUrlEncoded
    @POST("auth/login")
    Call<SignInModel> getLoginResponse(@FieldMap HashMap<String, String> authData);

    @FormUrlEncoded
    @POST("auth/emailexist")
    Call<JsonObject> checkEmailExist(@FieldMap HashMap<String, String> authData);

    @FormUrlEncoded
    @POST("auth/signup")
    Call<SignInModel> signUp(@FieldMap HashMap<String, String> authData);

    @FormUrlEncoded
    @POST("auth/recovery")
    Call<SignInModel> forgetPass(@FieldMap HashMap<String, String> authData);

    @FormUrlEncoded
    @POST("auth/reset")
    Call<SignInModel> resetPass(@FieldMap HashMap<String, String> authData);

    @GET("locations/states")
    Call<StateModel> getState();

    @GET("user/patients")
    Call<PatientModel> getPatientList(@Query("token") String token);
}