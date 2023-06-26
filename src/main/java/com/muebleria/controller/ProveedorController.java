package com.muebleria.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.muebleria.model.Cliente;
import com.muebleria.model.Proveedor;
import com.muebleria.repository.IProveedorRepository;

@Controller
public class ProveedorController {

	@Autowired
	private IProveedorRepository proRepo;

	// Proveedor
	public String manteProveedor(Model model) {
		return "regproveedor";
	}

	@GetMapping("/proveedor/acciones/listar")
	public String listarProveedor(Model model) {
		listarPro(model);
		return "listarProveedor";
	}

	/*
	 * // Insert
	 * 
	 * @GetMapping("/proveedor/acciones/registrar") public String
	 * registrarProveedor(@ModelAttribute Model model) {
	 * model.addAttribute("proveedores", new Proveedor()); return "crudproveedores";
	 * }
	 * 
	 * @PostMapping("/proveedor/registrado") public String
	 * guardarUsuario(@ModelAttribute("proveedores") Proveedor proveedor, Model
	 * model) { System.out.println(proveedor); //guarde en la tabla usando
	 * repository try { proRepo.save(proveedor); model.addAttribute("mensaje",
	 * "Proveedor Registrado Exitosamente"); model.addAttribute("claseMensaje",
	 * "alert alert-success"); } catch (Exception e) { model.addAttribute("mensaje",
	 * "Error en el Registro"); model.addAttribute("claseMensaje",
	 * "alert alert-danger"); } return "crudproveedores"; }
	 */

	@GetMapping("/proveedor/acciones/registrar")
	public String cargaryEnviarDatos(Model model) {
		model.addAttribute("proveedor", new Proveedor());
		return "crudproveedores";
	}

	@PostMapping("/leer/registro/proveedor")
	public String leerDatos(@ModelAttribute Proveedor proveedor, Model model) {
		System.out.println(proveedor);
		try {
			proRepo.save(proveedor);
			model.addAttribute("mensaje", "Registro de Mascota");
		} catch (Exception e) {
			model.addAttribute("mensaje", " Error en el Registro de Mascota");
		}
		return "crudproveedores";
	}

	// Update
	@GetMapping("/{id}/acciones/proveedor/actualizar")
	public String buscarProductoModForm(@PathVariable("id") int id, Model model) {
		try {
			Proveedor proveedor = proRepo.findById(id).orElse(new Proveedor());
			model.addAttribute("proveedor", proveedor);
			return "actualizarProveedor";
		} catch (Exception e) {
			return "listarProveedor";
		}
	}

	@PostMapping("/proveedor/acciones/actualizar")
	public String actualizarProveedor(@ModelAttribute("proveedor") Proveedor proveedor, Model model) {
		try {
			model.addAttribute("proveedor", proveedor);
			model.addAttribute("lstProveedor", proRepo.findAll());
			System.out.println("\n\n id=" + proveedor.getId_proveedor() + "\n\n" + proveedor.getRaz_soc() + "\n\n"
					+ proveedor.getRuc());
			proRepo.save(proveedor);
			model.addAttribute("mensaje", "Actualización Ok");
		} catch (Exception e) {
			System.out.println("Error :( " + e.getMessage());
		}
		return "listarProveedor";
	}

	// Delete

	@GetMapping("/{id}/acciones/proveedor/eliminar")
	public String buscarProveedorElmForm(@PathVariable("id") int id, Model model) {
		Proveedor proveedor = proRepo.findById(id).orElse(null);
		model.addAttribute("proveedor", proveedor);
		return "eliminarProveedor";
	}

	@PostMapping("/proveedor/eliminar")
	public String eliminarEmpleado(@ModelAttribute("proveedor") Proveedor proveedor, Model model) {
	    try {
	        proRepo.delete(proveedor);
	        model.addAttribute("mensaje", "Proveedor eliminado exitosamente");
	    } catch (Exception e) {
	        model.addAttribute("mensaje", "Error al eliminar al proveedor");
	    }
	    listarProveedor(model);
	    return "listarproveedor";
	}

	// METODOS
	void listarPro(Model model) {
		model.addAttribute("lstProveedor", proRepo.findAll());
	}

}
