package com.giabao.finalproject.service;

import com.giabao.finalproject.model.UserEntity;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface IUserService {

    @GET("users")
    Call<List<UserEntity>> getAllUsers();

    @POST("users")
    Call<UserEntity> create(@Body UserEntity item);
}
