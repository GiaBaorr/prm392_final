package com.giabao.finalproject.service;

import com.giabao.finalproject.model.UserEntity;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface IUserService {

    @GET("users")
    Call<List<UserEntity>> getAllUsers();
}
