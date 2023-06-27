package com.muebleria.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class MantenedorController {

	@Autowired
	ClienteController cliController;
	@Autowired
	EmpleadoController empController;
	@Autowired
	ProductoController prodController;
	@Autowired
	ProveedorController provController;
	
	@GetMapping("/mantenedor/cliente")
	public String crudClientes(Model model, HttpServletRequest request,HttpSession session,HttpServletResponse response) {
		cliController.abrirPagCliente(model);
		return "mantenedorcliente";
	}
	@GetMapping("/mantenedor/empleado")
	public String crudEmpleados(Model model, HttpServletRequest request,HttpSession session,HttpServletResponse response) {
		empController.manteEmpleado(model);
		return "mantenedorempleado";
	}
	@GetMapping("/mantenedor/producto")
	public String crudProductos(Model model, HttpServletRequest request,HttpSession session,HttpServletResponse response) {
		prodController.manteProducto(model);
		return "mantenedorProducto";
	}
	@GetMapping("/mantenimiento/proveedor")
	public String crudProveedores(Model model, HttpServletRequest request,HttpSession session,HttpServletResponse response) {
		provController.abrirPagProveedor(model);
		return "mantenedorproveedor";
	}
}
