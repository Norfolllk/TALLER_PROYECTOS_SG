package com.krakedev.proyectos.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.krakedev.proyectos.entidades.Empleado;
import com.krakedev.proyectos.repositories.EmpleadoRepository;


@Service
public class EmpleadoService {

    private final EmpleadoRepository repository;

    public EmpleadoService(EmpleadoRepository repository) {
        this.repository = repository;
    }

    public Empleado guardar(Empleado entidad) {
        return repository.save(entidad);
    }

    public List<Empleado> listar() {
        return repository.findAll();
    }

    public Empleado buscar(int id) {
        Optional<Empleado> resultado = repository.findById(id);
        return resultado.orElse(null);
    }

    public Empleado actualizar(int id, Empleado datos) {

        Empleado existente = buscar(id);

        if (existente == null) {
            return null;
        }

        existente.setNombre(datos.getNombre());
        existente.setCargo(datos.getCargo());

        return repository.save(existente);
    }

    public boolean eliminar(int id) {

        Empleado existente = buscar(id);

        if (existente == null) {
            return false;
        }

        repository.deleteById(id);

        return true;
    }
}
