package com.muebleria.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.muebleria.model.Producto;

public interface IProductoRepository extends JpaRepository<Producto, Integer>{

}
