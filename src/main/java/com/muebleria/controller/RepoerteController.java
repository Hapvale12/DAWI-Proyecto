package com.muebleria.controller;

import java.io.OutputStream;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

@Controller
public class RepoerteController {

	@Autowired
	private DataSource dataSource; // javax.sql
	@Autowired
	private ResourceLoader resourceLoader;
	
	@GetMapping("/reporte/producto")
	public void cargarProducto(HttpServletResponse response) {
		response.setHeader("Content-Disposition", "inline;"); 
		response.setContentType("application/pdf");
		try {
			String ru = resourceLoader.getResource("classpath:reportes/reporte_producto.jasper").getURI().getPath();
			JasperPrint jasperPrint = JasperFillManager.fillReport(ru, null, dataSource.getConnection());
			OutputStream outStream = response.getOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
			} catch (Exception e) {
			e.printStackTrace();
			}
	}
	@GetMapping("/grafico/producto")
	public void cargarGrafProducto(HttpServletResponse response) {
		response.setHeader("Content-Disposition", "inline;"); 
		response.setContentType("application/pdf");
		try {
			String ru = resourceLoader.getResource("classpath:reportes/grafico_producto.jasper").getURI().getPath();
			JasperPrint jasperPrint = JasperFillManager.fillReport(ru, null, dataSource.getConnection());
			OutputStream outStream = response.getOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
			} catch (Exception e) {
			e.printStackTrace();
			}
	}
	@GetMapping("/reporte/empleado")
	public void cargarRepoEmpleado(HttpServletResponse response) {
		response.setHeader("Content-Disposition", "inline;"); 
		response.setContentType("application/pdf");
		try {
			String ru = resourceLoader.getResource("classpath:reportes/Reporte_Empleado.jasper").getURI().getPath();
			JasperPrint jasperPrint = JasperFillManager.fillReport(ru, null, dataSource.getConnection());
			OutputStream outStream = response.getOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
			} catch (Exception e) {
			e.printStackTrace();
			}
	}
	
	@GetMapping("/reporte/proveedor")
	public void cargarRepoProveedor(HttpServletResponse response) {
		response.setHeader("Content-Disposition", "inline;"); 
		response.setContentType("application/pdf");
		try {
			String ru = resourceLoader.getResource("classpath:reportes/ReporteProveedor.jasper").getURI().getPath();
			JasperPrint jasperPrint = JasperFillManager.fillReport(ru, null, dataSource.getConnection());
			OutputStream outStream = response.getOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
			} catch (Exception e) {
			e.printStackTrace();
			}
	}
}
