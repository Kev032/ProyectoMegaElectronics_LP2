package com.cibertec.interfaceService;

import java.util.List;
import java.util.Optional;

import com.cibertec.model.Categorias;

public interface ICategoriaService {
	
	public List<Categorias> listar();
	public Optional<Categorias> listarId(int id);
	public int guardar (Categorias c);
	public void eliminar (int id);

}
