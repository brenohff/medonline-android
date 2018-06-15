package com.brenohff.medonline.Connections;

import com.brenohff.medonline.Domain.Consulta;
import com.brenohff.medonline.Domain.Diagnostico;
import com.brenohff.medonline.Domain.Especialidade;
import com.brenohff.medonline.Domain.Exame;
import com.brenohff.medonline.Domain.Medico;
import com.brenohff.medonline.Domain.Mensagem;
import com.brenohff.medonline.Domain.Paciente;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
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


    ////////////////////////////////////////////////////////////////////////////////////////////////////
    ///// ESPECIALIDADE
    ////////////////////////////////////////////////////////////////////////////////////////////////////
    @GET("especialidade/buscaTodos")
    Call<List<Especialidade>> buscaTodos();

    @GET("especialidade/buscaEspecialidadePorID")
    Call<Especialidade> buscaEspecialidadePorID(@Query("idEspecialidade") Long idEspecialidade);


    ////////////////////////////////////////////////////////////////////////////////////////////////////
    ///// MEDICOS
    ////////////////////////////////////////////////////////////////////////////////////////////////////
    @POST("medico/salvar")
    Call<Void> salvarMedico(@Body Medico medico);

    @GET("medico/buscaMedicoPeloID")
    Call<Medico> buscaMedicoPeloID(@Query("idMedico") Long idMedico);

    @GET("medico/buscaMedicoPeloEmail")
    Call<Medico> buscaMedicoPeloEmail(@Query("email") String email);

    @GET("medico/buscaTodos")
    Call<List<Medico>> buscaTodosMedicos();

    @GET("medico/buscaMedicoPelaEspecialidade")
    Call<List<Medico>> buscaMedicoPelaEspecialidade(@Query("idEspecialidade") Long idEspecialidade);

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    ///// CONSULTAS
    ////////////////////////////////////////////////////////////////////////////////////////////////////
    @POST("consulta/salvar")
    Call<Consulta> salvarConsulta(@Body Consulta consulta);

    @PUT("consulta/atualizar")
    Call<Void> atualizarConsulta(@Body Consulta consulta);

    @GET("consulta/buscaConsultaPorPaciente")
    Call<List<Consulta>> buscaConsultaPorPaciente(@Query("idPaciente") Long idPaciente);

    @GET("consulta/buscaConsultaPorMedico")
    Call<List<Consulta>> buscaConsultaPorMedico(@Query("idMedico") Long idMedico);


    ////////////////////////////////////////////////////////////////////////////////////////////////////
    ///// MENSAGENS
    ////////////////////////////////////////////////////////////////////////////////////////////////////
    @POST("mensagem/salvar")
    Call<List<Mensagem>> salvarMesagem(@Body Mensagem mensagem);

    @GET("mensagem/buscaMensagensPorConsulta")
    Call<List<Mensagem>> buscaMensagensPorConsulta(@Query("idConsulta") Long idConsulta);

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    ///// EXAMES
    ////////////////////////////////////////////////////////////////////////////////////////////////////
    @POST("exame/salvar")
    Call<Void> salvarExame(@Body Exame exame);

    @GET("exame/buscaExamePelaConsulta")
    Call<List<Exame>> buscaExamePelaConsulta(@Query("idConsulta") Long idConsulta);


    ////////////////////////////////////////////////////////////////////////////////////////////////////
    ///// DIAGNOSTICO
    ////////////////////////////////////////////////////////////////////////////////////////////////////
    @POST("diagnostico/salvar")
    Call<Void> salvarDiagnostico(@Body Diagnostico diagnostico);

    @GET("diagnostico/buscaDiagnosticoPorConsulta")
    Call<Diagnostico> buscaDiagnosticoPorConsulta(@Query("idConsulta") Long idConsulta);
}
