package com.cibertec.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cibertec.interfaceService.ICategoriaService;
import com.cibertec.model.Categorias;

@Controller
@RequestMapping
public class ControladorCategorias {
	
	@Autowired
	private ICategoriaService service;
	
	@RequestMapping("/inicio/Categorias")
	public String principal() {
		return "index";
	}
	
	@GetMapping("/Categorias")
	public String listarCategoria(Model model) {
		List<Categorias> categoria = service.listar();
		model.addAttribute("categoria", categoria);
		return "listarCategoria";
	}
	
	@GetMapping("/editarCategoria/{id}")
	public String editar(@PathVariable int id, Model model) {
		Optional<Categorias> categoria = service.listarId(id);
		model.addAttribute("categoria", categoria);
		return "editarCategoria";
	}
	
	@GetMapping("eliminarCategoria/{id}")
	public String eliminar (Model model, @PathVariable int id) {
		service.eliminar(id);
		return "redirect:/Categorias";
	}

	@GetMapping("/agregarCategoria")
	public String agregar(Model model) {
		model.addAttribute("categoria", new Categorias());
		return "registrarCategoria";
	}
	
	@PostMapping("/guardarCategoria")
    public String guardar(@Validated Categorias c, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("error", "Por favor, agregue la categoría.");
            return "formulario"; // Vuelve al formulario con el mensaje de error
        }

        service.guardar(c);
        return "redirect:/Categorias";
    }

}
