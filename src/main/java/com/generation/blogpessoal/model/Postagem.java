package com.generation.blogpessoal.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;


@Entity
@Table(name = "tb_postagens")
public class Postagem {

    @NotNull(message = "Id NÃO ENCONTRADO")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message  = "O Atributo título é obrigatório e não pode ser vazio!")
    @Size(min = 5, max = 100, message = "O atríbuto titulo deve conter no minino 5 e no maximo 100 caracteres")
    private String titulo;

    @NotNull(message  = "O Atributo texto é obrigatorio!")
    @Size(min = 10, max = 1000, message = "O atríbuto titulo deve conter no minino 10 e no maximo 1000 caracteres")
    private String texto;


    @UpdateTimestamp
    private LocalDateTime data;


    @ManyToOne
    @JsonIgnoreProperties("postagem")
    private Tema tema;

    @ManyToOne
    @JsonIgnoreProperties("postagem")
    private Usuario usuario;

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Tema getTema() {
        return tema;
    }

    public void setTema(Tema tema) {
        this.tema = tema;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime dataTime) {
        this.data = data;
    }
}
