package com.diegoleal.xmen.entity;


import org.hibernate.annotations.Table;

import javax.persistence.*;
import java.util.List;

@Entity
public class Mutante {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private boolean mutante;

    private String secuencia;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isMutante() {
        return mutante;
    }

    public void setMutante(boolean mutante) {
        this.mutante = mutante;
    }

    public String getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(String secuencia) {
        this.secuencia = secuencia;
    }
}
