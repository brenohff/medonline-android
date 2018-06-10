package com.brenohff.medonline.Memory;

import com.brenohff.medonline.Domain.Medico;
import com.brenohff.medonline.Domain.Paciente;

public class MainData {

    private static MainData instance = null;
    private Paciente paciente;
    private Medico medico;

    private MainData() {
    }

    public static MainData getInstance() {
        if (instance == null) {
            instance = new MainData();
        }

        return instance;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }
}
