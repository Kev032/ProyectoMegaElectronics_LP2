package com.cibertec.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import com.cibertec.interfaceService.IMarcaService;
import com.cibertec.model.Marca;


@Controller

public class ControladorMarca {
	
	@Autowired
	private IMarcaService service;
	
	@GetMapping("/listarMarca")
	public String listarMarca(Model model) {
		List<Marca> marca = service.listar();
		model.addAttribute("marca", marca);
		return "listarMarca";
	}

	@GetMapping("/editarMarca/{id}")
	public String editarMarca(@PathVariable int id, Model model) {
		Optional<Marca> marca = service.listarId(id);
		model.addAttribute("marca", marca);
		return "editarMarca";
	}

	@GetMapping("eliminarMarca/{id}")
	public String eliminarMarca(Model model, @PathVariable int id) {
		service.eliminar(id);
		return "redirect:/listarMarca";
	}

	@GetMapping("/agregarMarca")
	public String agregarMarca(Model model) {
		model.addAttribute("marca", new Marca());
		return "registrarMarca";
	}

	@PostMapping("/guardarMarca")
	public String guardar(@Validated Marca c, Model model) {
		service.guardar(c);
		return "redirect:/listarMarca";

	}
}
