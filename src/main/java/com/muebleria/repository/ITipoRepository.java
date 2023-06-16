package com.muebleria.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.muebleria.model.TipoProducto;

public interface ITipoRepository extends JpaRepository<TipoProducto, Integer>{

}
