package com.brenohff.medonline.Domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

public class Paciente implements Serializable {

    private static final long serialVersionUID = -5521092415402813636L;

    private Long idPaciente;
    private Endereco endereco;
    private Set<Consulta> consulta;
    private String email;
    private Date dtNascimento;
    private String nome;
    private String observacao;
    private String senha;
    private TipoSexo sexo;


    //GETTERS AND SETTERS

    public Date getIdade() {
        return dtNascimento;
    }

    public void setIdade(Date dtNascimento) {
        this.dtNascimento = dtNascimento;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public TipoSexo getSexo() {
        return sexo;
    }

    public void setSexo(TipoSexo sexo) {
        this.sexo = sexo;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public Set<Consulta> getConsulta() {
        return consulta;
    }

    public void setConsulta(Set<Consulta> consulta) {
        this.consulta = consulta;
    }

    public Long getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(Long idPaciente) {
        this.idPaciente = idPaciente;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDtNascimento() {
        return dtNascimento;
    }

    public void setDtNascimento(Date dtNascimento) {
        this.dtNascimento = dtNascimento;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
