package com.muebleria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.muebleria.model.Usuario;


@Repository

public interface IUsuarioRepository extends JpaRepository<Usuario, Integer> {
	
	Usuario findByUsuarioAndClave(String usuario, String clave);
	
	Usuario findByUsuario(String usuario);

}
