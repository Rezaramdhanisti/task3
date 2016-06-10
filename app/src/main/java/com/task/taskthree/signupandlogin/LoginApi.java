package com.task.taskthree.signupandlogin;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;


public interface LoginApi {
    @GET("/users")
    Call<Users> getUsers();


    @GET("/users")

    Call<User> getUser(@Path("id") String user_id);


    @PUT("/users")

    Call<User> updateUser(@Path("id") int user_id, @Body User user);


    @POST("/users")

    Call<User> saveUser(@Body User user);

}
