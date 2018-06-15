package com.brenohff.medonline.Domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;


public class Consulta implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long idConsulta;
    private Date dtConsulta;
    private String assunto;
    private Medico medico;
    private Paciente paciente;
    private List<Exame> exame;
    private Set<Diagnostico> diagnostico;
    private List<Mensagem> mensagens;
    private Boolean isFinalizada;


    //GETTERS AND SETTERS

    public Long getIdConsulta() {
        return idConsulta;
    }

    public void setIdConsulta(Long idConsulta) {
        this.idConsulta = idConsulta;
    }

    public Date getDtConsulta() {
        return dtConsulta;
    }

    public void setDtConsulta(Date dtConsulta) {
        this.dtConsulta = dtConsulta;
    }

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public List<Exame> getExame() {
        return exame;
    }

    public void setExame(List<Exame> exame) {
        this.exame = exame;
    }

    public Set<Diagnostico> getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(Set<Diagnostico> diagnostico) {
        this.diagnostico = diagnostico;
    }

    public List<Mensagem> getMensagens() {
        return mensagens;
    }

    public void setMensagens(List<Mensagem> mensagens) {
        this.mensagens = mensagens;
    }

    public Boolean getFinalizada() {
        return isFinalizada;
    }

    public void setFinalizada(Boolean finalizada) {
        isFinalizada = finalizada;
    }
}
