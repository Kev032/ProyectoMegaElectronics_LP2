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

import com.cibertec.interfaceService.ICategoriaService;
import com.cibertec.interfaceService.IMarcaService;
import com.cibertec.interfaceService.IProductoService;
import com.cibertec.model.Categorias;
import com.cibertec.model.Marca;
import com.cibertec.model.Productos;
import com.cibertec.report.ProductoExporterExcel;
import com.cibertec.report.ProductoExporterPDF;
import com.lowagie.text.DocumentException;

import jakarta.servlet.http.HttpServletResponse;

@Controller

public class ControladorProductos {

	@Autowired
	private IProductoService service;

	@Autowired
	private ICategoriaService categoriaService;
	
	@Autowired
	private IMarcaService marcaService;
	
	
	@GetMapping("/inicio")
	public String principal() {
		return "index";
	}
	@GetMapping("/Productos")
	public String listarProducto(Model model) {
		List<Productos> producto = service.listar();
		
		model.addAttribute("producto", producto);
		model.addAttribute("categoriaService", categoriaService);
		model.addAttribute("marcaService", marcaService);
		return "listarProducto";
	}
	
	@GetMapping("/editarProducto/{id}")
	public String editar(@PathVariable int id, Model model) {
		Optional<Productos> producto = service.listarId(id);
		List<Marca> listMarca = marcaService.listar();
		List<Categorias> listCategoria = categoriaService.listar();
		
		model.addAttribute("producto", producto);
		model.addAttribute("marca", listMarca);
		model.addAttribute("categoria", listCategoria);
		return "editarProducto";
	}
	
	@GetMapping("eliminarProducto/{id}")
	public String eliminar (Model model, @PathVariable int id) {
		service.eliminar(id);
		return "redirect:/Productos";
	}

	@GetMapping("/agregarProducto")
	public String agregar(Model model) {
		model.addAttribute("producto", new Productos());
		List<Marca> listMarca = marcaService.listar();
		List<Categorias> listCategoria = categoriaService.listar();
		
		model.addAttribute("marca", listMarca);
		model.addAttribute("categoria", listCategoria);
		return "registrarProducto";
	}
	
	@PostMapping("/guardarProducto")
	public String guardar(@Validated Productos c, Model model) {
		service.guardar(c);
		return "redirect:/Productos";
	}
	
	@GetMapping("/exportarProductoPDF")
	public void exportarProductosPDF(HttpServletResponse response) throws DocumentException, IOException {
		response.setContentType("application/pdf");
		
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String fechaActual = dateFormatter.format(new Date());
		
		String cabecera = "Content-Disposition";
		String valor = "attachment; filename=Productos_"+ fechaActual + ".pdf";
		
		response.setHeader(cabecera, valor);
		
		List<Productos> productos = service.listar();
		
		ProductoExporterPDF exporter = new ProductoExporterPDF(productos, categoriaService, marcaService);
	    exporter.exportar(response);
	}

	@GetMapping("/exportarProductoExcel")
	public void exportarProductosExcel(HttpServletResponse response) throws DocumentException, IOException {
		response.setContentType("application/octec-stream");
		
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String fechaActual = dateFormatter.format(new Date());
		
		String cabecera = "Content-Disposition";
		String valor = "attachment; filename=Clientes_"+ fechaActual + ".xlsx";
		
		response.setHeader(cabecera, valor);
		
		List<Productos> productos = service.listar();
		
		ProductoExporterExcel exporter = new ProductoExporterExcel(productos, categoriaService, marcaService);
		exporter.exportarExcel(response);
	}

	@GetMapping("/consultaproducto")
	public String consultaProducto(Model model,@Param("palabraClave") String palabraClave) {
		List<Productos> listProducto = service.listaProducto(palabraClave);

		model.addAttribute("producto", listProducto);
		model.addAttribute("categoriaService", categoriaService);
		model.addAttribute("marcaService", marcaService);
		model.addAttribute("palabraClave",palabraClave);
		return "consultaProducto";
	}
	
	//
	
	@GetMapping("/reporteProductos")
	public String reporteProductos(Model model) {
		List<Productos> producto = service.listar();
		
		model.addAttribute("producto", producto);
		model.addAttribute("categoriaService", categoriaService);
		model.addAttribute("marcaService", marcaService);
		return "reporteProducto";
	}

}