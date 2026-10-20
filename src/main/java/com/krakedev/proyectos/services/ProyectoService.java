package com.krakedev.proyectos.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.krakedev.proyectos.entidades.Proyecto;
import com.krakedev.proyectos.repositories.ProyectoRepository;


@Service
public class ProyectoService {

    private final ProyectoRepository repository;

    public ProyectoService(ProyectoRepository repository) {
        this.repository = repository;
    }

    public Proyecto guardar(Proyecto entidad) {
        return repository.save(entidad);
    }

    public List<Proyecto> listar() {
        return repository.findAll();
    }

    public Proyecto buscar(int id) {
        Optional<Proyecto> resultado = repository.findById(id);
        return resultado.orElse(null);
    }

    public Proyecto actualizar(int id, Proyecto datos) {

        Proyecto existente = buscar(id);

        if (existente == null) {
            return null;
        }

        existente.setNombre(datos.getNombre());
        existente.setDescripcion(datos.getDescripcion());
        existente.setFechaInicio(datos.getFechaInicio());

        return repository.save(existente);
    }

    public boolean eliminar(int id) {

        Proyecto existente = buscar(id);

        if (existente == null) {
            return false;
        }

        repository.deleteById(id);

        return true;
    }
}
