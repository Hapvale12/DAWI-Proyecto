package com.muebleria.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import com.muebleria.model.Empleado;
import com.muebleria.repository.IEmpleadoRepository;
import com.muebleria.repository.IPuestoRepository;

@Controller
public class EmpleadoController {
	
	@Autowired
	private IPuestoRepository puesRepo;
	
	@Autowired
	private IEmpleadoRepository empRepo;
	
	//Empleado
	public String manteEmpleado(Model model) {
		cargarComboEmp(model);
		return "crudempleados";
	}
	
	@GetMapping("/empleado/acciones/listar")
	public String listarEmpleado(Model model) {
		listarEmp(model);
		return "listempleado";
	}
	
	//Insert
	@PostMapping("/empleado/acciones/registrar")
	public String registrarEmpleado(@ModelAttribute Empleado empleado ,Model model) {
		try {
			empRepo.save(empleado);
			model.addAttribute("mensaje", "Registro Ok");
			cargarComboEmp(model);
		} catch (Exception e) {
			System.out.println("Error :( " + e.getMessage());
		}
		return "crudempleados";
	}
	
	//Update
	@GetMapping("/{id}/empleado/acciones/actualizar")
	public String buscarProductoModForm(@PathVariable("id") int id, Model model) {
		try {
			Empleado empleado = empRepo.findById(id).orElse(new Empleado());
			model.addAttribute("empleado", empleado);
			model.addAttribute("lstPuesto", puesRepo.findAll());
			return "actualizaempleado";
		}catch(Exception e) {
			return "listempleado";
		}
	}
	
	@PostMapping("/empleado/acciones/actualizar")
	public String actualizarEmpleado(@ModelAttribute("empleado") Empleado empleado ,Model model) {
		try {
			model.addAttribute("empleado", empleado);
			model.addAttribute("lstPuesto", puesRepo.findAll());
			empRepo.save(empleado);
			model.addAttribute("mensaje", "Actualización Ok");
			listarEmp(model);
		} catch (Exception e) {
			System.out.println("Error :( " + e.getMessage());
		}
		return "actualizaempleado";
	}
	//Delete
	@GetMapping("/{id}/acciones/eliminar")
	public String mostrarFormularioEliminacion(@PathVariable("id") int id, Model model) {
	    Empleado empleado = empRepo.findById(id).orElse(null);
	    model.addAttribute("empleado", empleado);
	    return "eliminarEmpleado";
	}
	@PostMapping("/empleado/acciones/eliminar")
	public String eliminarEmpleado(@ModelAttribute("empleado") Empleado empleado, Model model) {
	    try {
	        empRepo.delete(empleado);
	        model.addAttribute("mensaje", "Empleado eliminado exitosamente");
	    } catch (Exception e) {
	        model.addAttribute("mensaje", "Error al eliminar el empleado");
	        System.out.println("Error al eliminar el empleado: " + e.getMessage());
	    }
	    listarEmp(model);
	    return "listempleado";
	}
	
	
	//metodos
	void cargarComboEmp(Model model) {
		model.addAttribute("lstPuesto", puesRepo.findAll());
		model.addAttribute("empleado", new Empleado());
	}
	void listarEmp(Model model) {
		model.addAttribute("lstEmpleado", empRepo.findAll());
	}
	
	
	//Filtros

	public String filtrarEmpleadoXCat(Model model) {
		listarEmp(model);
		return "listempleado";
	}
	@GetMapping("/empleado/acciones/filtrar/")
	public String filtrarEmpleado(Model model) {
		
	        return "consultaempleado";
        
	}
}
