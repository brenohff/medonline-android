package com.brenohff.medonline.Domain;

import java.io.Serializable;
import java.util.Date;

public class Exame implements Serializable{

	private static final long serialVersionUID = -4597136780748713679L;

	private Long idExame;
	
	private String tipo;
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
