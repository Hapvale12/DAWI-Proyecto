package com.muebleria.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.muebleria.model.Usuario;
import com.muebleria.repository.ITiposUsuarioRepository;
import com.muebleria.repository.IUsuarioRepository;


@Controller
public class UsuarioController {
	//onjetos de repositorio
	@Autowired
	private ITiposUsuarioRepository repoUsu;
	
	@Autowired
	private IUsuarioRepository repoUsuario;
		
	@PostMapping("/usuario/validado")
	public String validar(@ModelAttribute("usuarios") Usuario usuario, Model model) {
		System.out.println(usuario);
		Usuario u;
		u = repoUsuario.findByUsuarioAndClave(usuario.getUsuario() , usuario.getClave());
		System.err.println(u);
		if (u == null) {
			//model.addAttribute("usuario", new Usuario());
			model.addAttribute("mensaje", "usuario o clave incorrecta");
			return "loginusu";			
		}else {
			model.addAttribute("usuario", u);
			return "index";
		}
	}
	
	//vista registrar
	@GetMapping("/usuario/registrar")
	public String cargarMantenimiento(Model model) {
		model.addAttribute("usuarios", new Usuario());
		//enviar listados para generar los combos
		model.addAttribute("lstTipos", repoUsu.findAll());
		return "crudusuarios";
	}
	
	
	//listar usuarios
	@GetMapping("/usuario/listado")
	public String generarListado(Model model) {
			model.addAttribute("lstUsuarios", repoUsuario.findAll());
		
		return"listusuarios" ;
		
	}
	
	//actualizar usuarios
	@PostMapping("/usuario/actualizar")
	public String actualizarEmpleado(@ModelAttribute("usuarios") Usuario usuario ,Model model) {
		try {
			repoUsuario.save(usuario);
			model.addAttribute("mensaje", "Actualización Ok");
			generarListado(model);
		} catch (Exception e) {
			System.out.println("Error :( " + e.getMessage());
		}
		return "actualizaempleado";
	}
	
	// eliminar usuarios
	//Delete
		@GetMapping("/{id}/usuario/eliminar")
		public String mostrarFormularioEliminacion(@PathVariable("id") int id, Model model) {
		    Usuario u = repoUsuario.findById(id).orElse(null);
		    model.addAttribute("usuarios", u);
		    return "eliminausu";
		}
		@PostMapping("/usuario/eliminar")
		public String eliminarUsuario(@ModelAttribute("usuarios") Usuario usuario, Model model) {
		    try {
		    	repoUsuario.delete(usuario);
		        model.addAttribute("mensaje", "Usuario eliminado exitosamente");
		    } catch (Exception e) {
		        model.addAttribute("mensaje", "Error al eliminar el usuario");
		        System.out.println("Error al eliminar el empleado: " + e.getMessage());
		    }
		    generarListado(model);
		    return "listusuarios";
		}
	
	
	//registrar usuario + combo tipos
	@PostMapping("/usuario/registrado")
	public String guardarUsuario(@ModelAttribute("usuarios") Usuario usuario, Model model) {
		model.addAttribute("lstTipos", repoUsu.findAll());
		System.out.println(usuario);
		//guarde en la tabla usando repository
		try {
			repoUsuario.save(usuario);
			model.addAttribute("mensaje", "Usuario Registrado Exitosamente");
			model.addAttribute("claseMensaje", "alert alert-success");
		} catch (Exception e) {
			model.addAttribute("mensaje", "Error en el Registro");
			model.addAttribute("claseMensaje", "alert alert-danger");
		}
		return "crudusuarios";
	}
	
	
	

}
