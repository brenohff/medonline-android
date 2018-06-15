package com.brenohff.medonline.Domain;

import java.io.Serializable;
import java.util.List;

public class Diagnostico implements Serializable {

    private static final long serialVersionUID = 6143178888116852498L;

    private Long idDiagnostico;
    private String resultado;
    private String descricao;
    private List<Receita> receita;
    private Consulta consulta;


    //GETTERS AND SETTERS
    public Long getIdDiagnostico() {
        return idDiagnostico;
    }

    public void setIdDiagnostico(Long idDiagnostico) {
        this.idDiagnostico = idDiagnostico;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<Receita> getReceita() {
        return receita;
    }

    public void setReceita(List<Receita> receita) {
        this.receita = receita;
    }

    public Consulta getConsulta() {
        return consulta;
    }

    public void setConsulta(Consulta consulta) {
        this.consulta = consulta;
    }
}
