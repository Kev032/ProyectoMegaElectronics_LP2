package com.cibertec.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cibertec.interfaceService.IClienteService;
import com.cibertec.model.Clientes;
import com.cibertec.report.ClientesExporterExcel;
import com.cibertec.report.ClientesExporterPDF;
import com.lowagie.text.DocumentException;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping
public class ControladorClientes {
	
	@Autowired
	private IClienteService service;
	
	@RequestMapping("/inicio/Clientes")
	public String principal() {
		return "index";
	}
	
	@GetMapping("/Clientes")
	public String listarCliente(Model model) {
		List<Clientes> cliente = service.listar();
		model.addAttribute("cliente", cliente);
		return "listarCliente";
	}
	
	@GetMapping("/editarCliente/{id}")
	public String editar(@PathVariable int id, Model model) {
		Optional<Clientes> cliente = service.listarId(id);
		model.addAttribute("cliente", cliente);
		return "editarCliente";
	}
	
	@GetMapping("eliminarCliente/{id}")
	public String eliminar (Model model, @PathVariable int id) {
		service.eliminar(id);
		return "redirect:/Clientes";
	}

	@GetMapping("/agregarCliente")
	public String agregar(Model model) {
		model.addAttribute("cliente", new Clientes());
		return "registrarCliente";
	}
	
	@PostMapping("/guardarCliente")
	public String guardar(@Validated Clientes c, Model model) {
		service.guardar(c);
		return "redirect:/Clientes";
	}
	
	@GetMapping("/exportarClientePDF")
	public void exportarClientesPDF(HttpServletResponse response) throws DocumentException, IOException {
		response.setContentType("application/pdf");
		
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String fechaActual = dateFormatter.format(new Date());
		
		String cabecera = "Content-Disposition";
		String valor = "attachment; filename=Clientes_"+ fechaActual + ".pdf";
		
		response.setHeader(cabecera, valor);
		
		List<Clientes> clientes = service.listar();
		
		ClientesExporterPDF exporter = new ClientesExporterPDF(clientes);
		exporter.exportar(response);
	}
	
	@GetMapping("/exportarClienteExcel")
	public void exportarClientesExcel(HttpServletResponse response) throws DocumentException, IOException {
	    response.setContentType("application/octec-stream");

	    DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH_mm_ss");
	    String fechaActual = dateFormatter.format(new Date());

	    String cabecera = "Content-Disposition";
	    String valor = "attachment; filename=Clientes_" + fechaActual + ".xlsx";

	    response.setHeader(cabecera, valor);

	    List<Clientes> clientes = service.listar();

	    ClientesExporterExcel exporter = new ClientesExporterExcel(clientes);
	    exporter.exportarExcel(response);
	}


	
	@GetMapping("/consultaCliente")
	public String consultaCliente(Model model,@Param("palabraClave") String palabraClave) {
		List<Clientes> listCliente = service.listaCliente(palabraClave);

		model.addAttribute("cliente", listCliente);
		model.addAttribute("palabraClave",palabraClave);
		return "consultaCliente";
	}
	
	@GetMapping("/reporteClientes")
	public String reporteClientes(Model model) {
		List<Clientes> cliente = service.listar();
		model.addAttribute("cliente", cliente);
		return "reporteClientes";
	}
}
