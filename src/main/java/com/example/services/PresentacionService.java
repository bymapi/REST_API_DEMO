package com.example.services;

import java.util.List;

import com.example.entities.Presentacion;

public interface PresentacionService {

    // Métodos para meter datos de muestra
    public List<Presentacion> findAll();
    public Presentacion findById(int id);
    public void save(Presentacion presentacion);
    public void delete(Presentacion presentacion);

}
