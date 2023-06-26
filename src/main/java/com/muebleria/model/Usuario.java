package com.muebleria.model;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Entity
@Table(name = "tb_usuario")
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {
	@Id
	@Column(name="id_usuario")
	private int  id_usuario;
	private String nombre;
	private String apellido;
	private String usuario;
	private String clave;
	private String fnacim;
	private int id_tipousuario;
	
	@ManyToOne
	@JoinColumn(name = "id_tipousuario", insertable = false, updatable = false)
	private TiposUsuario oTipo;
}