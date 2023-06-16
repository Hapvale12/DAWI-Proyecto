package com.muebleria.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.muebleria.model.Usuario;


@Controller
public class ProyectoController {
	@GetMapping("/")
	public String iniciar(Model model) {
		model.addAttribute("usuarios", new Usuario());
		return "loginusu";
	}
	
	@GetMapping("/home")
	public String Inicio(Model model){
		return "index";
	}
}
