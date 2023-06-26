package com.muebleria.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.muebleria.model.Proveedor;
import com.muebleria.repository.IProveedorRepository;

@Controller
public class ProveedorController {

	@Autowired
	private IProveedorRepository repoProve;

	@GetMapping("/proveedor/acciones/mantenimiento")
	public String abrirPagProveedor(Model model) {
		model.addAttribute("proveedor", new Proveedor());
		model.addAttribute("updatevalidacion", null);
		generarLista(model);
		return "mantenedorProveedor";
	}
		
	//Listar Clientes
	public String generarLista(Model model) {
		model.addAttribute("listaProveedores", repoProve.findAll());
		
		//Lo que panchito no supo resolver
		
		List<Proveedor> lista = new ArrayList<Proveedor>();
		lista = repoProve.findAll();
		Object fila = repoProve.count();
		int posicion = (Integer.parseInt(fila.toString())-1);
		model.addAttribute("ultiId", lista.get(posicion).getId_proveedor());
		model.addAttribute("nuevoId", lista.get(posicion).getId_proveedor() + 1);
		model.addAttribute("proveedor", new Proveedor());
		return "mantenedorProveedor";
	}
		
	//Registrar Clientes
	@PostMapping("/proveedor/acciones/registrar")
	public String registrarProveedor(@ModelAttribute Proveedor proveedor, Model model) {

		List<Proveedor> lista = new ArrayList<Proveedor>();
		lista = repoProve.findAll();
		Object fila = repoProve.count();
		int posicion = (Integer.parseInt(fila.toString())-1);
		lista.get(posicion).getId_proveedor();
		proveedor.setId_proveedor(lista.get(posicion).getId_proveedor()+1);
		try {
			repoProve.save(proveedor);
			model.addAttribute("mensaje", "Registro Exitoso");
		}catch (Exception e) {
			model.addAttribute("mensaje", "Error al registrar");
			System.out.println(e.getMessage());
		}
		generarLista(model);
		return "mantenedorProveedor";
	}
	
	//Actualizar
	
	@PostMapping("/proveedor/acciones/actualizar")
	public String actualizarProveedor(@ModelAttribute("proveedor") Proveedor proveedor ,Model model) {
		model.addAttribute("listaProveedores", repoProve.findAll());;
		try {
			repoProve.save(proveedor);
			model.addAttribute("mensaje", "Actualización exitosa.");
			generarLista(model);
		} catch (Exception e) {
			System.out.println("Error : " + e.getMessage());
		}
		return "mantenedorProveedor";
	}
	
	//Eliminar
	@GetMapping("/proveedor/acciones/eliminar/{id}")
	public String FormEliminarCliente(@PathVariable("id") int id, Model model) {
		Proveedor proveedor = repoProve.findById(id).orElse(null);
		model.addAttribute("proveedor", proveedor);
		return "eliminarProveedor";
	}
	
	@PostMapping("/proveedor/acciones/eliminar")
	public String eliminarProveedor(@ModelAttribute("proveedor") Proveedor proveedor, Model model) {
	    try {
	    	repoProve.delete(proveedor);
	    	model.addAttribute("mensaje", "Proveedor eliminado con éxito.");
			generarLista(model);
	    } catch (Exception e) {
	    	model.addAttribute("mensaje", "Error al eliminar el Proveedor.");
			generarLista(model);
	    }
	    return "mantenedorProveedor";
	}
	
	//Mostrar Cliente a Actualizar
	@GetMapping("/proveedor/acciones/actualizar/{id}")
	public String CargarProveedorXId(@PathVariable("id") int id, Model model) {
		model.addAttribute("updatevalidacion", "not null");
		try {
			generarLista(model);
			Proveedor proveedor = repoProve.findById(id).orElse(new Proveedor());
			model.addAttribute("prov", proveedor);
			generarLista(model);
			return "mantenedorProveedor";
		}catch(Exception e) {
			System.out.println(e.getLocalizedMessage());
			return "listarProveedor";
		}
	}

}
