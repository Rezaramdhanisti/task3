package com.task.taskthree.signupandlogin;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

/**
 * Created by Personal on 1/27/2016.
 */
public interface LoginApi {
    @GET("/task/login")
    Call<Users> getUsers();


    @GET("/task/login")

    Call<User> getUser(@Path("id") String user_id);


    @PUT("/task/login")

    Call<User> updateUser(@Path("id") int user_id, @Body User user);


    @POST("/task/login")

    Call<User> saveUser(@Body User user);

}
