package com.example.rafa.tfg.rest;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by Rafa on 23/01/2018.
 */

public interface RestInterface {

    @GET("/api/users")
    Call<List<usuAdapter>> getAllUsuarios();

    @GET("/api/users/id/{idusuario}")
    Call<List<usuAdapter>> getUsuario(@Path("idusuario") int idusuario);

    @GET("/api/usu/checklogin/{email}/{pass}")
    Call<usuAdapter> checkLogin(@Path("email") String email, @Path("pass") String pass);

    @PUT("/api/users/new")
    Call<usuAdapter> addUsuario(@Body usuAdapter user);

    @DELETE("/api/users/delete")
    Call<usuAdapter> deleteUsuario(@Body usuAdapter user);

    @POST("/usuario/insertUsu")
    Call<Void> addUser(@Body usuAdapter user);

    @GET("/api/usu/getAlergenoDataId/{idusuario}")
    Call<List<usuAdapter>> getAlergenoDataId(@Path("idusuario") int idusuario);


}
