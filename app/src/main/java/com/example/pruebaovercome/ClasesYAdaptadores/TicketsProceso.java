package com.example.pruebaovercome.ClasesYAdaptadores;

public class TicketsProceso {

    private String ID, Titulo, Fecha, Usuario, Departamento, Incidencia, Gravedad, Version, Descripcion, Archivo, Etapa, Archivado;

    public TicketsProceso(String ID, String titulo, String fecha, String usuario, String departamento, String incidencia, String gravedad, String version, String descripcion, String archivo, String etapa, String archivado) {
        this.ID = ID;
        this.Titulo = titulo;
        this.Fecha = fecha;
        this.Usuario = usuario;
        this.Departamento = departamento;
        this.Incidencia = incidencia;
        this.Gravedad = gravedad;
        this.Version = version;
        this.Descripcion = descripcion;
        this.Archivo = archivo;
        this.Etapa = etapa;
        this.Archivado = archivado;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getTitulo() {
        return Titulo;
    }

    public void setTitulo(String titulo) {
        Titulo = titulo;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }

    public String getUsuario() {
        return Usuario;
    }

    public void setUsuario(String usuario) {
        Usuario = usuario;
    }

    public String getDepartamento() {
        return Departamento;
    }

    public void setDepartamento(String departamento) {
        Departamento = departamento;
    }

    public String getIncidencia() {
        return Incidencia;
    }

    public void setIncidencia(String incidencia) {
        Incidencia = incidencia;
    }

    public String getGravedad() {
        return Gravedad;
    }

    public void setGravedad(String gravedad) {
        Gravedad = gravedad;
    }

    public String getVersion() {
        return Version;
    }

    public void setVersion(String version) {
        Version = version;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getArchivo() {
        return Archivo;
    }

    public void setArchivo(String archivo) {
        Archivo = archivo;
    }

    public String getEtapa() {
        return Etapa;
    }

    public void setEtapa(String etapa) {
        Etapa = etapa;
    }

    public String getArchivado() {
        return Archivado;
    }

    public void setArchivado(String archivado) {
        Archivado = archivado;
    }
}