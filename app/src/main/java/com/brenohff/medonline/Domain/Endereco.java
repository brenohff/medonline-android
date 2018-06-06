package com.brenohff.medonline.Domain;

import java.util.Set;

public class Endereco {

	private Long idEndereco;
	
	private String cep;
	private String complemento;
	private String logradouro;

	private Set<Medico> medico;
	private Set<Paciente> paciente;

	
	//GETTERS AND SETTERS
	public Long getId() {
		return idEndereco;
	}

	public void setId(Long idEndereco) {
		this.idEndereco = idEndereco;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public Set<Medico> getMedico() {
		return medico;
	}

	public void setMedico(Set<Medico> medico) {
		this.medico = medico;
	}

	public Set<Paciente> getPaciente() {
		return paciente;
	}

	public void setPaciente(Set<Paciente> paciente) {
		this.paciente = paciente;
	}
	
}
