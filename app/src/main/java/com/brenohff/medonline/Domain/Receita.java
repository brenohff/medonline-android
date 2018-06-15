package com.brenohff.medonline.Domain;

import java.io.Serializable;
import java.util.Date;

public class Receita implements Serializable {

    private static final long serialVersionUID = -872597292408168024L;

    private Long idReceita;
    private String descricao;
    private Date dtReceita;
    private Diagnostico diagnostico;


    //GETTERS AND SETTERS
    public Long getIdReceita() {
        return idReceita;
    }

    public void setIdReceita(Long idReceita) {
        this.idReceita = idReceita;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getDtReceita() {
        return dtReceita;
    }

    public void setDtReceita(Date dtReceita) {
        this.dtReceita = dtReceita;
    }

    public Diagnostico getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(Diagnostico diagnostico) {
        this.diagnostico = diagnostico;
    }
}
