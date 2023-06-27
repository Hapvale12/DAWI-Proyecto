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
		model.addAttribute("empleado", new Empleado());
		model.addAttribute("updatevalidacion", null);
		generarListado(model);
		return "mantenedorEmpleado";
	}
	
	public String generarListado(Model model) {
		model.addAttribute("listaEmpleados", empRepo.findAll());
		model.addAttribute("listaPuestos", puesRepo.findAll());
		List<Empleado> lista = new ArrayList<Empleado>();
		lista = empRepo.findAll();
		Object fila = empRepo.count();
		int posicion = (Integer.parseInt(fila.toString())-1);
		model.addAttribute("ultiId", lista.get(posicion).getId_empleado());
		model.addAttribute("nuevoId", lista.get(posicion).getId_empleado() + 1);
		return "mantenedorEmpleado";
	}
	
	//Registrar Cliente
	@PostMapping("/empleado/acciones/registrar")
	public String registrarEmplado(@ModelAttribute("empleado") Empleado empleado, Model model) {

		try {
			//Leer los datos ingresados
			empRepo.save(empleado);
			model.addAttribute("mensaje", "Registro Ok");
			generarListado(model);
		} catch (Exception e) {
	
			model.addAttribute("mensaje", "Error al registrar");
			System.out.println("Error: " + e.getMessage());
			generarListado(model);
		}
		return "mantenedorEmpleado";
	}
	
	//Mostrar Empleado a Actualizar
	@GetMapping("/empleado/acciones/actualizar/{id}")
	public String CargarEmpleadoXId(@PathVariable("id") int id, Model model) {
		model.addAttribute("updatevalidacion", "not null");
		try {
			Empleado empleado = empRepo.findById(id).orElse(new Empleado());
			model.addAttribute("empleado", empleado);
			generarListado(model);
			return "mantenedorEmpleado";
		}catch(Exception e) {
			System.out.println(e.getLocalizedMessage());
			return "mantenedorEmpleado";
		}
	}

	//Actualizar
	@PostMapping("/empleado/acciones/actualizar")
	public String actualizarEmpleado(@ModelAttribute("empleado") Empleado empleado, Model model) {
		try {
			empRepo.save(empleado);
			model.addAttribute("empleado", empleado);
			model.addAttribute("mensaje", "Actualización exitosa.");
			model.addAttribute("listaEmpleados", empRepo.findAll());
			generarListado(model);
		} catch (Exception e) {
			System.out.println("Error : " + e.getMessage());
			model.addAttribute("mensaje", "Error al actualizar");
			model.addAttribute("listaEmpleados", empRepo.findAll());
			generarListado(model);
		}
		return "empleadoRedirigir";
	}	
	@GetMapping("/redirigirEmpleado")
	public String redirigir(Model model) {
		manteEmpleado(model);
		model.addAttribute("mensaje", "Actualización exitosa.");
		model.addAttribute("listaEmpleados", empRepo.findAll());
		return "mantenedorEmpleado";
	}
	
	//Buscar para eliminar empleado
		@GetMapping("/empleado/acciones/eliminar/{id}")
		public String buscarEmpleadoAEliminar(@PathVariable("id") int id, Model model) {
		    Empleado empleado = empRepo.findById(id).orElse(null);
		    model.addAttribute("empleado", empleado);
		    return "eliminarEmpleado";
		}
		@PostMapping("/empleado/acciones/eliminar")
		public String eliminarEmpleado(@ModelAttribute("empleado") Empleado empleado, Model model) {
		    try {
		        empRepo.delete(empleado);
		        model.addAttribute("mensaje", "Cliente eliminado exitosamente");
		    } catch (Exception e) {
		        model.addAttribute("mensaje", "Error al eliminar al cliente");
		    }
		    generarListado(model);
		    return "mantenedorEmpleado";
		}
}
