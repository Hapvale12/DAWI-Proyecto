package com.muebleria.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.muebleria.model.Cliente;

public interface IClienteRepository extends JpaRepository<Cliente, Integer>{

}
