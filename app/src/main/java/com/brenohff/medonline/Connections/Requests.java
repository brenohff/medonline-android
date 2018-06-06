package com.brenohff.medonline.Connections;

import com.brenohff.medonline.Domain.Paciente;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by breno on 07/08/2017.
 */

public interface Requests {

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    ///// PACIENTES
    ////////////////////////////////////////////////////////////////////////////////////////////////////
    @POST("paciente/salvar")
    Call<Void> salvarPaciente(@Body Paciente paciente);

    @GET("paciente/buscaPacientePeloID")
    Call<Paciente> buscaPacientePeloID(@Query("idPaciente") Long idPaciente);

    @GET("paciente/buscaPacientePeloEmail")
    Call<Paciente> buscaPacientePeloEmail(@Query("email") String email);


}
