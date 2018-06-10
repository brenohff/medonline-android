package com.brenohff.medonline.Domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

public class Medico implements Serializable {

    private static final long serialVersionUID = -5888636092094648442L;

    private Long idMedico;

    private String nome;
    private String crm;
    private Especialidade especialidade;
    private Date dtNascimento;
    private String email;

    private Endereco endereco;

    private Set<Consulta> consulta;

    private TipoSexo sexo;


    //region GETTERS AND SETTERS
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCrm() {
        return crm;
    }

    public void setCrm(String crm) {
        this.crm = crm;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public TipoSexo getSexo() {
        return sexo;
    }

    public void setSexo(TipoSexo sexo) {
        this.sexo = sexo;
    }

    public Set<Consulta> getConsulta() {
        return consulta;
    }

    public void setConsulta(Set<Consulta> consulta) {
        this.consulta = consulta;
    }

    public Long getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(Long idMedico) {
        this.idMedico = idMedico;
    }

    public Especialidade getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(Especialidade especialidade) {
        this.especialidade = especialidade;
    }

    public Date getDtNascimento() {
        return dtNascimento;
    }

    public void setDtNascimento(Date dtNascimento) {
        this.dtNascimento = dtNascimento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    //endregion
}
