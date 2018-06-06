package com.brenohff.medonline.Memory;

import com.brenohff.medonline.Domain.Paciente;

public class MainData {

    private static MainData instance = null;
    private Paciente paciente;

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
}
