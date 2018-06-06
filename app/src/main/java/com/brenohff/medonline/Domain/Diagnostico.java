package com.brenohff.medonline.Domain;

import java.io.Serializable;
import java.util.Set;

public class Diagnostico implements Serializable{

	private static final long serialVersionUID = 6143178888116852498L;

	private Long idDiagnostico;

	private Set<Receita> receita;
	
	private String resultado;
	private String descricao;

	private Consulta consulta;

	
	//GETTERS AND SETTERS
	public Long getIdDiagnostico() {
		return idDiagnostico;
	}

	public void setIdDiagnostico(Long idDiagnostico) {
		this.idDiagnostico = idDiagnostico;
	}

	public Set<Receita> getReceita() {
		return receita;
	}

	public void setReceita(Set<Receita> receita) {
		this.receita = receita;
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

	public Consulta getConsulta() {
		return consulta;
	}

	public void setConsulta(Consulta consulta) {
		this.consulta = consulta;
	}

}
