package com.muebleria.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_producto")
public class Producto {
	@Id
	private int id_prod;
    private int id_proveedor;
    private String des_prod;
	private int id_tipoprod;
	private int stock;
	private double precio;
	
	//Para tipo producto
	@ManyToOne
	@JoinColumn(name = "id_tipoprod", insertable = false, updatable = false)
	private TipoProducto objTipo;
	
	//Para Proveedor
	@ManyToOne
	@JoinColumn(name = "id_proveedor", insertable = false, updatable = false)
	private Proveedor objProveedor;
	
}
