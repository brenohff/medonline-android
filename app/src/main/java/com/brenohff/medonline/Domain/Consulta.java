package com.brenohff.medonline.Domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;


public class Consulta implements Serializable{

	private Long idConsulta;
	private Date dtConsulta;
	private Medico medico;
	private Paciente paciente;
	private String assunto;
	private Set<Exame> exame;
	private Set<Diagnostico> diagnostico;

	
	//GETTERS AND SETTERS
	public Long getId() {
		return idConsulta;
	}

	public void setId(Long idConsulta) {
		this.idConsulta = idConsulta;
	}

	public Date getDt_consulta() {
		return dtConsulta;
	}

	public void setDt_consulta(Date dtConsulta) {
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

	public Set<Exame> getExame() {
		return exame;
	}

	public void setExame(Set<Exame> exame) {
		this.exame = exame;
	}

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

	public Set<Diagnostico> getDiagnostico() {
		return diagnostico;
	}

	public void setDiagnostico(Set<Diagnostico> diagnostico) {
		this.diagnostico = diagnostico;
	}
	
}
