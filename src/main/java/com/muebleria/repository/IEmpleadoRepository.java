package com.muebleria.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.muebleria.model.Empleado;

public interface IEmpleadoRepository extends JpaRepository<Empleado, Integer>{

}
