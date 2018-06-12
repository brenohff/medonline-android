package com.brenohff.medonline.Domain;

import java.io.Serializable;
import java.util.Date;


public class Mensagem implements Serializable {
    private Long idMensagem;
    private String texto;
    private Date data;
    private boolean fromPaciente;
    private Consulta consulta;

    public Long getIdMensagem() {
        return idMensagem;
    }

    public void setIdMensagem(Long idMensagem) {
        this.idMensagem = idMensagem;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public boolean isFromPaciente() {
        return fromPaciente;
    }

    public void setFromPaciente(boolean fromPaciente) {
        this.fromPaciente = fromPaciente;
    }

    public Consulta getConsulta() {
        return consulta;
    }

    public void setConsulta(Consulta consulta) {
        this.consulta = consulta;
    }
}
