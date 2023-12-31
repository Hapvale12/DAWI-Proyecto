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
import com.muebleria.repository.IClienteRepository;

@Controller
public class ClienteController {
	
	@Autowired
	private IClienteRepository repoCli;

	public String abrirPagCliente(Model model) {
		model.addAttribute("cliente", new Cliente());
		model.addAttribute("updatevalidacion", null);
		generarLista(model);
		return "mantenedorCliente";
	}
	
	//Registrar Cliente
	@PostMapping("/cliente/acciones/registrar")
	public String registrarProducto(@ModelAttribute Cliente cliente, Model model) {
	
		List<Cliente> lista = new ArrayList<Cliente>();
		lista = repoCli.findAll();
		Object fila = repoCli.count();
		int posicion = (Integer.parseInt(fila.toString())-1);
		model.addAttribute("nuevoId", lista.get(posicion).getId_cliente() + 1);
		try {
			//Leer los datos ingresados
			repoCli.save(cliente);
			model.addAttribute("mensaje", "Registro Ok");
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
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
	@GetMapping("/cliente/acciones/listar")
	public String listarClientes(Model model) {
		generarLista(model);
		return "mantenedorCliente";
	}
	//buscar Cliente
	@GetMapping("/cliente/acciones/actualizar/{id}")
	public String CargarClienteXId(@PathVariable("id") int id, Model model) {
		model.addAttribute("updatevalidacion", "not null");
		try {
			generarLista(model);
			Cliente cliente = repoCli.findById(id).orElse(new Cliente());
			model.addAttribute("cliente", cliente);
			model.addAttribute("listaClientes", repoCli.findAll());;
			return "mantenedorCliente";
		}catch(Exception e) {
			System.out.println(e.getMessage());
			return "mantenedorCliente";
		}
	}	
	//Modificar Cliente
		@PostMapping("/cliente/acciones/actualizar")
		public String actualizarProducto(@ModelAttribute("cliente") Cliente cliente ,Model model) {
			try {
				model.addAttribute("cliente", cliente);
				model.addAttribute("listaClientes", repoCli.findAll());;
				repoCli.save(cliente);
				model.addAttribute("mensaje", "Actualización Ok");
				listarClientes(model);
			} catch (Exception e) {
				System.out.println("Error :( " + e.getMessage());
			}
			return "mantenedorCliente";
		}
	//Buscar para eliminar cliente
	@GetMapping("/cliente/acciones/eliminar/{id}")
	public String buscarProductoElmForm(@PathVariable("id") int id, Model model) {
	    Cliente cliente = repoCli.findById(id).orElse(null);
	    model.addAttribute("cliente", cliente);
	    return "eliminarcliente";
	}
	@PostMapping("/cliente/acciones/eliminar")
	public String eliminarCliente(@ModelAttribute("cliente") Cliente cliente, Model model) {
	    try {
	        repoCli.delete(cliente);
	        model.addAttribute("mensaje", "Cliente eliminado exitosamente");
	    } catch (Exception e) {
	        model.addAttribute("mensaje", "Error al eliminar al cliente");
	    }
	    listarClientes(model);
	    return "mantenedorCliente";
	}
	
}
