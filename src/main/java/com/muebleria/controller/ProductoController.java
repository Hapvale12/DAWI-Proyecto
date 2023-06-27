package com.muebleria.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import com.muebleria.model.Producto;
import com.muebleria.repository.IProductoRepository;
import com.muebleria.repository.IProveedorRepository;
import com.muebleria.repository.ITipoRepository;

@Controller
public class ProductoController {
	
	@Autowired
	private ITipoRepository repoTipo;
	
	@Autowired
	private IProveedorRepository repoProv;
	
	@Autowired
	private IProductoRepository repoProd;
	
	//Producto
	public String manteProducto(Model model) {
		model.addAttribute("producto", new Producto());
		model.addAttribute("updatevalidacion", null);
		generarListado(model);
		return "mantenedorProducto";
	}
	
	//Generar listado
	public String generarListado(Model model) {
		model.addAttribute("producto", new Producto());
		model.addAttribute("lstTipo", repoTipo.findAll());
		model.addAttribute("lstProveedor", repoProv.findAll());
		return "mantenedorProducto";
	}
		
	//Registrar Producto
	@PostMapping("/producto/acciones/registrar")
	public String registrarProducto(@ModelAttribute("producto") Producto producto, Model model) {
			try {
			//Leer los datos ingresados
			repoProd.save(producto);
			model.addAttribute("mensaje", "Registro Ok");
			generarListado(model);
		} catch (Exception e) {	
			model.addAttribute("mensaje", "Error al registrar");
			System.out.println("Error: " + e.getMessage());
			generarListado(model);
		}
		return "mantenedorProducto";
	}
		
	//Buscar codigo para Actualizar
	@GetMapping("/producto/acciones/actualizar/{id}")
	public String buscarProductoxId(@PathVariable("id") int id, Model model) {
		model.addAttribute("updatevalidacion", "not null");
		try {
			Producto producto = repoProd.findById(id).orElse(new Producto());
			model.addAttribute("producto", producto);
			generarListado(model);
			return "mantenedorProducto";
		}catch(Exception e) {
			System.out.println(e.getLocalizedMessage());
			return "mantenedorProducto";
		}
	}

	//Actualizar
	@PostMapping("/producto/acciones/actualizar")
	public String actualizarProducto(@ModelAttribute("producto") Producto producto, Model model) {
		try {
			repoProd.save(producto);
			model.addAttribute("producto", producto);
			model.addAttribute("mensaje", "Actualización exitosa.");
			model.addAttribute("lstProducto", repoProd.findAll());
			generarListado(model);
		} catch (Exception e) {
			System.out.println("Error : " + e.getMessage());
			model.addAttribute("mensaje", "Error al actualizar");
			model.addAttribute("lstProducto", repoProd.findAll());
			generarListado(model);
		}
		return "pagActualizarProds";
	}	
	//Redirigir a producto	
	@GetMapping("/redirigirProducto")
	public String redirigir(Model model) {
		manteProducto(model);
		model.addAttribute("mensaje", "Actualización exitosa.");
		model.addAttribute("lstProducto", repoProd.findAll());
		return "mantenedorProducto";
	}
	
	//Buscar codigo para Eliminar
	@GetMapping("/producto/acciones/eliminar/{id}")
	public String buscarProductoEliminarxId(@PathVariable("id") int id, Model model) {
	    Producto producto = repoProd.findById(id).orElse(null);
	    model.addAttribute("producto", producto);
	    return "eliminarProducto";
	}
	//Eliminar
	@PostMapping("/producto/acciones/eliminar")
	public String eliminarProducto(@ModelAttribute("producto") Producto producto, Model model) {
	    try {
	        repoProd.delete(producto);
	        model.addAttribute("mensaje", "Producto eliminado exitosamente");
	    } catch (Exception e) {
	        model.addAttribute("mensaje", "Error al eliminar al cliente");
	    }
	    	generarListado(model);
		return "mantenedorProducto";
	}
}
