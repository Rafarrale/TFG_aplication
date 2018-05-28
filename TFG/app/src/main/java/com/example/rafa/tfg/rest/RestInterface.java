package com.example.rafa.tfg.rest;
import com.example.rafa.tfg.adapters.CasaAdapterIni;
import com.example.rafa.tfg.adapters.DispositivosAdapter;
import com.example.rafa.tfg.adapters.estadoAlarmaAdapter;
import com.example.rafa.tfg.adapters.usuAdapter;
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

    @GET("/usuario/giveUsu/{usu}/{pass}")
    Call<usuAdapter> checkLogin(@Path("usu") String email, @Path("pass") String pass);

    @GET("/usuario/giveUsuario/todosUsuarios/{passCasa}")
    Call<ArrayList<usuAdapter>> getTodosUsuarios(@Path("passCasa") String passCasa);

    @GET("/usuario/recuperaPass/{email}")
    Call<Void> getPass (@Path("email") String email);

    @GET("/usuario/compruebaUser/{user}")
    Call<Void> compruebaUser(@Path("user") String user);

    @GET("/casa/giveHome/todasCasas/{numSerie}")
    Call<List<CasaAdapterIni>> getCasas(@Path ("numSerie") String numSerie);

    @GET("/casa/giveEstado/{homeUsu}/{estadoAlarma}")
    Call<estadoAlarmaAdapter> estadoAlarmaCasa(@Path ("homeUsu") String homeUsu, @Path("estadoAlarma") String casaAdapterIni);

    @GET("/dispositivo/giveDispTodos/{homeUsu}/{tipo}")
    Call<List<DispositivosAdapter>> getTodosDispositivos(@Path ("homeUsu") String homeUsu, @Path("tipo") String tipo);

    @GET("/dispositivo/giveDispTodosNuevos/{campoClaveDisp}")
    Call<List<DispositivosAdapter>> getTodosDispositivosNuevos(@Path("campoClaveDisp") String campoClaveDisp);

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

    @POST("/dispositivo/insertaDispositivoCasa")
    Call<Void> addDispositivoCasa(@Body DispositivosAdapter dispositivosAdapter);

    @POST("/notificacion/insertaToken")
    Call<Token> anadeToken(@Body Token token);

    @POST("/notificacion/insertaTokenCasa")
    Call<Void> anadeTokenCasa(@Body Token token);

}
