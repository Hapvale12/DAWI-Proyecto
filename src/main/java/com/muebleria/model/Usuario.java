package com.muebleria.model;



import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
@Table(name = "tb_usuario")
public class Usuario {
	@Id
	private int  id_usuario;
	private String nombre;
	private String apellido;
	private String usuario;
	private String clave;
	private String fnacim;
	private int id_tipousuario;
	

}