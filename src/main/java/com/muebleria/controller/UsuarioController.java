package com.muebleria.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import com.muebleria.model.Usuario;
import com.muebleria.repository.ITiposUsuarioRepository;
import com.muebleria.repository.IUsuarioRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@Controller
public class UsuarioController {
	//onjetos de repositorio
	@Autowired
	private ITiposUsuarioRepository repoUsu;
	@Autowired
	private IUsuarioRepository repoUsuario;
		
	@PostMapping("/usuario/validado")
	public String validar(@ModelAttribute("usuarios") Usuario usuario, Model model,HttpSession session, HttpServletResponse response, HttpServletRequest request) {
		Usuario u;
		u = repoUsuario.findByUsuarioAndClave(usuario.getUsuario() , usuario.getClave());
		System.err.println(u);
		if (u == null) {
			model.addAttribute("mensaje", "usuario o clave incorrecta");
			return "loginusu";			
		}else {
			model.addAttribute("usuarios", u);
			model.addAttribute("sessionId", session.getId());
			String ruta = "/img/perfil/"+u.getId_usuario() + ".jpg";
			model.addAttribute("rutaImagen", ruta);
			
			// Obtener la ID de la sesión
			HttpSession sesion = request.getSession();
	        System.out.println("ID de sesión: " + sesion);
	        sesion.setAttribute("usuario", u.getUsuario());
	        sesion.setAttribute("clave", u.getClave());
			return "index";
			
		}
	}
	
	
	//Cerrar sesion//
	@GetMapping("/logout")
	public String cerrarSesion(HttpServletRequest request,HttpSession session,HttpServletResponse response, Model model) {
		model.addAttribute("usuarios", new Usuario());
		        session.invalidate();
	    return "loginusu"; // Redirige a la página de inicio de sesión
	}
	
	
	//vista registrar
	@GetMapping("/usuario/registrar")
	public String cargarMantenimiento(Model model) {
		model.addAttribute("usuario", new Usuario());
		model.addAttribute("lstTipos", repoUsu.findAll());
		return "crudusuarios";
	}
	//listar usuarios
	@GetMapping("/usuario/acciones")
	public String generarListado(Model model) {
		model.addAttribute("lstUsuarios", repoUsuario.findAll());
		return"listusuarios" ;
	}
	@GetMapping("/{id}/usuario/")
	public String paginaActualizar(Model model, @PathVariable("id") int id) {
		try {
			Usuario usuario = repoUsuario.findById(id).orElse(new Usuario());
			model.addAttribute("usuario", usuario);
			model.addAttribute("lstTipos", repoUsu.findAll());
		} catch (Exception e) {
			System.out.println("ERROR: " + e.getMessage());
		}
		return "actualizausu";
	}
	
		
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
	
	
	//registrar/actualizar usuario
	@PostMapping("/usuario")
	public String guardarUsuario(@ModelAttribute Usuario usuario, Model model) {
		
		String pag = "crudusuarios";
		System.out.println(usuario.getUsuario());
		System.out.println(usuario);
		model.addAttribute("lstTipos", repoUsu.findAll());
		try {
			/*if(repoUsuario.findById(usuario.getId_usuario()).get() != null) {
				model.addAttribute("mensaje", "Usuario Actualizado Exitosamente");
				pag = "actualizausu";
			}else {
				model.addAttribute("mensaje", "Usuario Registrado Exitosamente");				
			}*/
			repoUsuario.save(usuario);
			model.addAttribute("claseMensaje", "alert alert-success");
			model.addAttribute("usuario", usuario);
			model.addAttribute("lstTipos", repoUsu.findAll());
		} catch (Exception e) {
			System.out.println("ERROR: " + e.getMessage());
			model.addAttribute("mensaje", "Error en el Registro");
			model.addAttribute("claseMensaje", "alert alert-danger");
		}
		return pag;
	}
	
	
	

}
