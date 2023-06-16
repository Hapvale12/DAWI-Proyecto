package com.muebleria.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="tb_puesto")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Puesto {
	@Id
	private int id_puesto;
	private String des_puesto;
}
