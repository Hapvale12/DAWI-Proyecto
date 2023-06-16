package com.muebleria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.muebleria.model.TiposUsuario;

@Repository
public interface ITiposUsuarioRepository extends JpaRepository<TiposUsuario, Integer> {

}
