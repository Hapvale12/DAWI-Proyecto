package com.muebleria.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.muebleria.model.Usuario;
import com.muebleria.repository.IUsuarioRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;



@Controller
public class ProyectoController {

	@Autowired
	private IUsuarioRepository repoUsuario;

	@GetMapping("/")
	public String iniciar(Model model) {
		model.addAttribute("usuarios", new Usuario());
		return "loginusu";
	}
	
	@GetMapping("/home")
	public String Inicio(Model model, HttpServletRequest request,HttpSession session,HttpServletResponse response){
		Usuario u;
		u = repoUsuario.findByUsuarioAndClave(session.getAttribute("usuario").toString() , session.getAttribute("clave").toString());
		model.addAttribute("usuarios", u);
		model.addAttribute("sessionId", session.getId());
		String ruta = "/img/perfil/"+u.getId_usuario() + ".jpg";
		model.addAttribute("rutaImagen", ruta);
		Mantenedores(model, request, session, response);
		return "index";
	}

	@GetMapping("/mantenedores")
	public String Mantenedores(Model model, HttpServletRequest request,HttpSession session,HttpServletResponse response) {
		Usuario u;
		u = repoUsuario.findByUsuarioAndClave(session.getAttribute("usuario").toString() , session.getAttribute("clave").toString());
		model.addAttribute("usuarios", u);
		String ruta = "/img/perfil/"+u.getId_usuario() + ".jpg";
		model.addAttribute("rutaImagen", ruta);
		model.addAttribute("sessionId", session.getId());
		return "index_mantenedores";
	}
	@GetMapping("/reportes")
	public String Reportes(Model model, HttpServletRequest request,HttpSession session,HttpServletResponse response) {
		Usuario u;
		u = repoUsuario.findByUsuarioAndClave(session.getAttribute("usuario").toString() , session.getAttribute("clave").toString());
		model.addAttribute("usuarios", u);
		String ruta = "/img/perfil/"+u.getId_usuario() + ".jpg";
		model.addAttribute("rutaImagen", ruta);
		model.addAttribute("sessionId", session.getId());
		return "index_reportes";
	}
}
