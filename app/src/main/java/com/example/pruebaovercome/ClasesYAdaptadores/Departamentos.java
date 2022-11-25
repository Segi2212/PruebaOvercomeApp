package com.example.pruebaovercome.ClasesYAdaptadores;

public class Departamentos {

    private String /*ID,*/ Departamento;

    public Departamentos(/*String ID,*/ String departamento) {
        //this.ID = ID;
        this.Departamento = departamento;
    }

    /*public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }*/

    public String getDepartamento() {
        return Departamento;
    }

    public void setDepartamento(String departamento) {
        Departamento = departamento;
    }
}
