package com.example.rafa.tfg.rest;
import com.example.rafa.tfg.adapters.CasaAdapterIni;
import com.example.rafa.tfg.adapters.DispositivosAdapter;
import com.example.rafa.tfg.adapters.estadoAlarmaAdapter;
import com.example.rafa.tfg.adapters.usuAdapter;
import com.example.rafa.tfg.clases.Casa;
import com.example.rafa.tfg.clases.Token;

import java.util.ArrayList;
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

    @GET("/api/users/id/{idusuario}")
    Call<List<usuAdapter>> getUsuario(@Path("idusuario") int idusuario);

    @GET("/usuario/giveUsu/{usu}/{pass}")
    Call<usuAdapter> checkLogin(@Path("usu") String email, @Path("pass") String pass);

    @GET("/usuario/giveUsuario/todosUsuarios")
    Call<ArrayList<usuAdapter>> getTodosUsuarios();

    @GET("/casa/giveHome/todasCasas")
    Call<List<CasaAdapterIni>> getCasas();

    @GET("/casa/giveEstado/{homeUsu}/{estadoAlarma}")
    Call<estadoAlarmaAdapter> estadoAlarmaCasa(@Path ("homeUsu") String homeUsu, @Path("estadoAlarma") String casaAdapterIni);

    @GET("/dispositivo/giveDispTodos/{homeUsu}")
    Call<List<DispositivosAdapter>> getTodosDispositivos(@Path ("homeUsu") String homeUsu);

    @GET("/dispositivo/giveDispTodosNuevos")
    Call<List<DispositivosAdapter>> getTodosDispositivosNuevos();

    @PUT("/api/users/new")
    Call<usuAdapter> addUsuario(@Body usuAdapter user);

/* No funciona antiguo metodo
    @DELETE("/casa/eliminaCasa")
    Call<Void> eliminaCasa(@Body CasaAdapterIni casaAdapterIni);
*/

    @HTTP(method = "DELETE", path = "/casa/eliminaCasa", hasBody = true)
    Call<Void> eliminaCasa(@Body CasaAdapterIni casaAdapterIni);

    @HTTP(method = "DELETE", path = "/usuario/eliminaUsuario", hasBody = true)
    Call<Void> eliminaUsuario(@Body usuAdapter usuAdapter);

    @POST("/usuario/insertUsu")
    Call<Void> addUser(@Body usuAdapter user);

    @POST("/usuario/actUsu")
    Call<Void> actualizaUsuario(@Body usuAdapter usuAdapter);

    @POST("/casa/giveHomeUsu")
    Call<Void> compruebaHomeUsu(@Body CasaAdapterIni homeUsu);

    @POST("/casa/insertCasa")
    Call<Void> addCasa(@Body CasaAdapterIni casaAdapterIni);

    @POST("/dispositivo/insertDispositivo")
    Call<DispositivosAdapter> addDispositivo(@Body DispositivosAdapter dispositivosAdapter);

    @POST("/notificacion/actualizaToken")
    Call<Token> actualizaToken(@Body Token token);

    @POST("/notificacion/insertaToken")
    Call<Token> enviaToken(@Body Token token);


}
