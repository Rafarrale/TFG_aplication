package com.example.rafa.tfg.rest;
import com.example.rafa.tfg.adapters.CasaAdapterIni;
import com.example.rafa.tfg.adapters.estadoAlarmaAdapter;
import com.example.rafa.tfg.adapters.usuAdapter;
import com.example.rafa.tfg.clases.Casa;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
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

    @GET("/usuario/giveUsu/{usu}/{pass}")
    Call<usuAdapter> checkLogin(@Path("usu") String email, @Path("pass") String pass);

    @GET("/casa/giveHome/todasCasas")
    Call<List<CasaAdapterIni>> getCasas();

    @GET("/casa/giveEstado/{homeUsu}/{estadoAlarma}")
    Call<estadoAlarmaAdapter> estadoAlarmaCasa(@Path ("homeUsu") String homeUsu, @Path("estadoAlarma") String casaAdapterIni);

    @PUT("/api/users/new")
    Call<usuAdapter> addUsuario(@Body usuAdapter user);

    @DELETE("/api/users/delete")
    Call<usuAdapter> deleteUsuario(@Body usuAdapter user);

/* No funciona antiguo metodo
    @DELETE("/casa/eliminaCasa")
    Call<Void> eliminaCasa(@Body CasaAdapterIni casaAdapterIni);
*/

    @HTTP(method = "DELETE", path = "/casa/eliminaCasa", hasBody = true)
    Call<Void> eliminaCasa(@Body CasaAdapterIni casaAdapterIni);


    @POST("/usuario/insertUsu")
    Call<Void> addUser(@Body usuAdapter user);

    @POST("/casa/giveHomeUsu")
    Call<Void> compruebaHomeUsu(@Body CasaAdapterIni homeUsu);
    
    @POST("/casa/insertCasa")
    Call<Void> addCasa(@Body CasaAdapterIni casaAdapterIni);

    @GET("/api/usu/getAlergenoDataId/{idusuario}")
    Call<List<usuAdapter>> getAlergenoDataId(@Path("idusuario") int idusuario);


}
