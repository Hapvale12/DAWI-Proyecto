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
import java.util.ArrayList;
import java.util.List;

@Controller
public class UsuarioController {

	
	@Autowired
	private ITiposUsuarioRepository repoTipoUsu;
	@Autowired
	private IUsuarioRepository repoUsuario;
	
	// Validar / Iniciar Sesión
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
	
	
	//Cerrar sesion
	@GetMapping("/logout")
	public String cerrarSesion(HttpServletRequest request,HttpSession session,HttpServletResponse response, Model model) {
		model.addAttribute("usuarios", new Usuario());
		        session.invalidate();
	    return "loginusu"; // Redirige a la página de inicio de sesión
	}
	
	
	//vista registrar
	@GetMapping("/usuario/abrir")
	public String cargarMantenimiento(Model model) {
		model.addAttribute("Usuario", new Usuario());
		model.addAttribute("updatevalidacion", null);
		generarListado(model);
		return "mantenedorUsuario";
	}
	//listar usuarios y tipos
	public void generarListado(Model model) {
		model.addAttribute("listaUsuarios", repoUsuario.findAll());
		model.addAttribute("lstTipos", repoTipoUsu.findAll());
		List<Usuario> lista = new ArrayList<Usuario>();
		lista = repoUsuario.findAll();
		Object fila = repoUsuario.count();
		int posicion = (Integer.parseInt(fila.toString())-1);
		model.addAttribute("ultiId", lista.get(posicion).getId_usuario());
		model.addAttribute("nuevoId", lista.get(posicion).getId_usuario() + 1);
	}
	
	//Registrar Usuario
	@PostMapping("/usuario/acciones/registrar")
	public String registrarProducto(@ModelAttribute("Usuario") Usuario usuario, Model model) {
	
		try {
			//Leer los datos ingresados
			repoUsuario.save(usuario);
			model.addAttribute("mensaje", "Registro Ok");
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		generarListado(model);
		return "mantenedorUsuario";
	}
	//Mostrar Cliente a Actualizar
	@GetMapping("/usuario/acciones/actualizar/{id}")
	public String CargarUsuarioXId(@PathVariable("id") int id, Model model) {
		model.addAttribute("updatevalidacion", "not null");
		try {
			Usuario usuario = repoUsuario.findById(id).orElse(new Usuario());
			model.addAttribute("Usuario", usuario);
			generarListado(model);
			return "mantenedorUsuario";
		}catch(Exception e) {
			System.out.println(e.getLocalizedMessage());
			return "mantenedorUsuario";
		}
	}
	//Actualizar
	@PostMapping("/usuario/acciones/actualizar")
	public String actualizarUsuario(@ModelAttribute("Usuario") Usuario usuario ,Model model) {
		try {
			repoUsuario.save(usuario);
			model.addAttribute("mensaje", "Actualización exitosa.");
		} catch (Exception e) {
			System.out.println("Error : " + e.getMessage());
			model.addAttribute("mensaje", "Error al actualizar");
		}
		generarListado(model);
		return "mantenedorUsuario";
	}
	
	//Abrir Eliminar Usuario
	@GetMapping("/usuario/acciones/eliminar/{id}")
	public String FormEliminarCliente(@PathVariable("id") int id, Model model) {
		Usuario usuario = repoUsuario.findById(id).orElse(null);
		model.addAttribute("Usuario", usuario);
		return "eliminarUsuario";
	}

	//Eliminar Usuario
	@PostMapping("/usuario/acciones/eliminar")
	public String eliminarUsuario(@ModelAttribute("Usuario") Usuario usuario, Model model) {
	    try {
	    	repoUsuario.delete(usuario);
	    	model.addAttribute("mensaje", "Usuario eliminado con éxito.");
			generarListado(model);
	    } catch (Exception e) {
	    	model.addAttribute("mensaje", "Error al eliminar el usuario.");
			generarListado(model);
	    }
	    return "mantenedorUsuario";
	}

}
