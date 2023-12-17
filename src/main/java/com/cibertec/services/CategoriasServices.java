package com.cibertec.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.cibertec.interfaceService.ICategoriaService;
import com.cibertec.interfaces.ICategorias;
import com.cibertec.model.Categorias;

@Service
public class CategoriasServices implements ICategoriaService{
	
	@Autowired
	private ICategorias data;

	@Override
	public List<Categorias> listar() {
		return data.findAll(Sort.by(Sort.Direction.ASC, "idCategoria"));
	}

	@Override
	public Optional<Categorias> listarId(int id) {
		return data.findById(id);
	}

	@Override
	public int guardar(Categorias c) {
		Categorias categoriaGuardada = data.save(c);
		if (categoriaGuardada != null) {
			return categoriaGuardada.getIdCategoria(); // Devuelve el ID de la categoría guardada
		} else {
			return 0; // Retorna 0 si no se pudo guardar la categoría
		}
	}

	@Override
	public void eliminar(int id) {
		data.deleteById(id);
		
	}

}
