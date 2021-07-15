package com.example.practicawebapiempleados;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Objects;

public class Empleados {

    private String IdEmpleado;
    private String Apellido;
    private String Oficio;
    private String Salario;
    private String Departamento;
    private String Comision;
    private String FechaAlta;
    private String JefeDirecto;

    public Empleados() {
    }

    public Empleados(String idEmpleado, String apellido, String oficio, String salario,
                     String departamento, String comision, String fechaAlta, String jefeDirecto) {
        IdEmpleado = idEmpleado;
        Apellido = apellido;
        Oficio = oficio;
        Salario = salario;
        Departamento = departamento;
        Comision = comision;
        FechaAlta = fechaAlta;
        JefeDirecto = jefeDirecto;
    }

    public String getIdEmpleado() {
        return this.IdEmpleado;
    }

    public void setIdEmpleado(String idEmpleado) {
        IdEmpleado = idEmpleado;
    }

    public String getApellido() {
        return this.Apellido;
    }

    public void setApellido(String apellido) {
        Apellido = apellido;
    }

    public String getOficio() {
        return this.Oficio;
    }

    public void setOficio(String oficio) {
        Oficio = oficio;
    }

    public String getSalario() {
        return this.Salario;
    }

    public void setSalario(String salario) {
        Salario = salario;
    }

    public String getDepartamento() {
        return this.Departamento;
    }

    public void setDepartamento(String departamento) {
        Departamento = departamento;
    }

    public String getComision() {
        return this.Comision;
    }

    public void setComision(String comision) {
        Comision = comision;
    }

    public String getFechaAlta() {
        return this.FechaAlta;
    }

    public void setFechaAlta(String fechaAlta) {
        FechaAlta = fechaAlta;
    }

    public String getJefeDirecto() {
        return this.JefeDirecto;
    }

    public void setJefeDirecto(String jefeDirecto) {
        JefeDirecto = jefeDirecto;
    }

    @Override
    public String toString() {
        return  Apellido ;
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Empleados empleados = (Empleados) o;
        return Objects.equals(Apellido, empleados.Apellido);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(Apellido);
    }
}
