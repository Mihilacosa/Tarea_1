package com.example.tarea_1;

public class  ListaNovelas {
    private String titulo;
    private String id;
    private String imagen;

    public ListaNovelas() {

    }

    public ListaNovelas(String titulo, String id, String imagen) {
        this.titulo = titulo;
        this.id = id;
        this.imagen = imagen;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
