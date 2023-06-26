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
	private ITipoRepository tipRepo;
	
	@Autowired
	private IProveedorRepository proRepo;
	
	@Autowired
	private IProductoRepository repoProd;
	
	//Cargar pagina producto
	public String cargaPagProd(Model model) {
		cargarComboProd(model);
		return "crudproductos";
	}
	
	//Listar productos
	@GetMapping("/producto/mantenimiento/listar")
	public String generarLista(Model model) {
		listarProducto(model);
		return "listarProducto";
	}
	
	//Registrar productos
	@PostMapping("/producto/acciones/registrar")
	public String registrarProducto(@ModelAttribute Producto producto, Model model) {
		try {
			//Leer los datos ingresados
			repoProd.save(producto);
			model.addAttribute("mensaje", "Registro Ok");
			cargarComboProd(model);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Error: ( " + e.getMessage());
		}
		return "crudproductos";
	}
	
	//buscar productos
	@GetMapping("/{id}/acciones/prod/actualizar")
	public String buscarProductoModForm(@PathVariable("id") int id, Model model) {
		try {
			Producto producto = repoProd.findById(id).orElse(new Producto());
			model.addAttribute("producto", producto);
			model.addAttribute("lstTipo", tipRepo.findAll());
			model.addAttribute("lstProveedor", proRepo.findAll());
			return "actualizarProducto";
		}catch(Exception e) {
			return "listarProducto";
		}
	}
	
	//Modificar Productos
	@PostMapping("/producto/acciones/actualizar")
	public String actualizarProducto(@ModelAttribute("producto") Producto producto ,Model model) {
		try {
			model.addAttribute("producto", producto);
			model.addAttribute("lstTipo", tipRepo.findAll());
			model.addAttribute("lstProveedor", proRepo.findAll());
			repoProd.save(producto);
			model.addAttribute("mensaje", "Actualización Ok");
			listarProducto(model);
		} catch (Exception e) {
			System.out.println("Error :( " + e.getMessage());
		}
		return "actualizarProducto";
	}
	
	//Buscar para eliminar producto
	@GetMapping("/{id}/acciones/prod/eliminar")
	public String buscarProductoElmForm(@PathVariable("id") int id, Model model) {
	    Producto producto = repoProd.findById(id).orElse(null);
	    model.addAttribute("producto", producto);
	    return "eliminarProducto";
	}
	
	@PostMapping("/producto/acciones/eliminar")
	public String eliminarEmpleado(@ModelAttribute("producto") Producto producto, Model model) {
	    try {
	        repoProd.delete(producto);
	        model.addAttribute("mensaje", "Producto eliminado exitosamente");
	    } catch (Exception e) {
	        model.addAttribute("mensaje", "Error al eliminar el producto");
	        System.out.println("Error al eliminar el producto: " + e.getMessage());
	    }
	    listarProducto(model);
	    return "listarProducto";
	}
	
	//Metodos
	void cargarComboProd(Model model) {
		model.addAttribute("producto", new Producto());
		model.addAttribute("lstTipo", tipRepo.findAll());
		model.addAttribute("lstProveedor", proRepo.findAll());
	}
	void listarProducto(Model model) {
		model.addAttribute("lstProducto", repoProd.findAll());
	}

}
