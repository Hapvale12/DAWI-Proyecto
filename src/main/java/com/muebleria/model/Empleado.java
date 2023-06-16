package com.muebleria.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="tb_empleado")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Empleado {
	@Id
	private int id_empleado;
	private String nombre;
	private String apellido;
	private int id_puesto;
	
	@ManyToOne
	@JoinColumn(name="id_puesto", insertable = false, updatable = false)
	private Puesto objPuesto;
}
