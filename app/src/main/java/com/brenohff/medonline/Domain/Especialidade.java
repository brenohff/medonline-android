package com.brenohff.medonline.Domain;

import java.io.Serializable;
import java.util.List;

public class Especialidade implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idEspecialidade;
    private String especialidade;
    private String descricao;
    private List<Medico> medicos;

    public Long getIdEspecialidade() {
        return idEspecialidade;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public String getDescricao() {
        return descricao;
    }

    public List<Medico> getMedicos() {
        return medicos;
    }
}
