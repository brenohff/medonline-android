package com.brenohff.medonline.Domain;

import java.io.Serializable;
import java.util.Date;

public class Exame implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idExame;
    private String tipo;
    private String descricao;
    private Date dtExame;
    private Consulta consulta;


    //GETTERS AND SETTERS

    public Long getIdExame() {
        return idExame;
    }

    public void setIdExame(Long idExame) {
        this.idExame = idExame;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getDtExame() {
        return dtExame;
    }

    public void setDtExame(Date dtExame) {
        this.dtExame = dtExame;
    }

    public Consulta getConsulta() {
        return consulta;
    }

    public void setConsulta(Consulta consulta) {
        this.consulta = consulta;
    }
}
