package com.brenohff.medonline.Domain;

import java.io.Serializable;
import java.util.Set;

public class Medico extends Usuario implements Serializable {

	private static final long serialVersionUID = -5888636092094648442L;

	private Long idMedico;
	private String crm;
	private Especialidade especialidade;
	private Set<Consulta> consulta;
	private Avaliacao avaliacao;

	// GETTERS AND SETTERS

	public String getCrm() {
		return crm;
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

	public Avaliacao getAvaliacao() {
		return avaliacao;
	}

	public void setAvaliacao(Avaliacao avaliacao) {
		this.avaliacao = avaliacao;
	}
}
