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

import com.muebleria.model.Cliente;
import com.muebleria.model.Empleado;
import com.muebleria.model.Producto;
import com.muebleria.repository.IClienteRepository;

@Controller
public class ClienteController {
	
	@Autowired
	private IClienteRepository repoCli;

	
	@GetMapping("/cliente/acciones/mantenimiento")
	public String abrirPagCliente(Model model) {
		model.addAttribute("cliente", new Cliente());
		model.addAttribute("updatevalidacion", null);
		generarLista(model);
		return "mantenedorCliente";
	}
		
	//Listar Clientes
	public String generarLista (Model model) {
		model.addAttribute("listaClientes", repoCli.findAll());
		
		//Lo que panchito no supo resolver
		
		List<Cliente> lista = new ArrayList<Cliente>();
		lista = repoCli.findAll();
		Object fila = repoCli.count();
		int posicion = (Integer.parseInt(fila.toString())-1);
		model.addAttribute("ultiId", lista.get(posicion).getId_cliente());
		model.addAttribute("nuevoId", lista.get(posicion).getId_cliente() + 1);
		model.addAttribute("cliente", new Cliente());
		return "mantenedorCliente";
	}
		
	//Registrar Clientes
	@PostMapping("/cliente/acciones/registrar")
	public String registrarCliente(@ModelAttribute Cliente cliente, Model model) {

		List<Cliente> lista = new ArrayList<Cliente>();
		lista = repoCli.findAll();
		Object fila = repoCli.count();
		int posicion = (Integer.parseInt(fila.toString())-1);
		lista.get(posicion).getId_cliente();
		cliente.setId_cliente(lista.get(posicion).getId_cliente()+1);
		try {
			repoCli.save(cliente);
			model.addAttribute("mensaje", "Registro Exitoso");
		}catch (Exception e) {
			model.addAttribute("mensaje", "Error al registrar");
			System.out.println(e.getMessage());
		}
		generarLista(model);
		return "mantenedorCliente";
	}
	
	//Actualizar
	
	@PostMapping("/cliente/acciones/actualizar")
	public String actualizarCliente(@ModelAttribute("cliente") Cliente cliente ,Model model) {
		model.addAttribute("listaClientes", repoCli.findAll());;
		try {
			repoCli.save(cliente);
			model.addAttribute("mensaje", "Actualización exitosa.");
			generarLista(model);
		} catch (Exception e) {
			System.out.println("Error : " + e.getMessage());
		}
		return "mantenedorCliente";
	}
	
	//Eliminar
	@GetMapping("/cliente/acciones/eliminar/{id}")
	public String FormEliminarCliente(@PathVariable("id") int id, Model model) {
		Cliente cliente = repoCli.findById(id).orElse(null);
		model.addAttribute("cliente", cliente);
		return "eliminarCliente";
	}
	
	@PostMapping("/cliente/acciones/eliminar")
	public String eliminarCliente(@ModelAttribute("cliente") Cliente cliente, Model model) {
	    try {
	    	repoCli.delete(cliente);
	    	model.addAttribute("mensaje", "Cliente eliminado con éxito.");
			generarLista(model);
	    } catch (Exception e) {
	    	model.addAttribute("mensaje", "Error al eliminar el cliente.");
			generarLista(model);
	    }
	    return "mantenedorCliente";
	}
	
	//Mostrar Cliente a Actualizar
	@GetMapping("/cliente/acciones/actualizar/{id}")
	public String CargarClienteXId(@PathVariable("id") int id, Model model) {
		model.addAttribute("updatevalidacion", "not null");
		try {
			generarLista(model);
			Cliente cliente = repoCli.findById(id).orElse(new Cliente());
			model.addAttribute("cli", cliente);
			generarLista(model);
			return "mantenedorCliente";
		}catch(Exception e) {
			System.out.println(e.getLocalizedMessage());
			return "listarProducto";
		}
	}

}
