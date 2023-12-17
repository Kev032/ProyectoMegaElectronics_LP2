package com.cibertec.interfaceService;

import java.util.List;
import java.util.Optional;

import com.cibertec.model.Marca;

public interface IMarcaService {
	
	public List<Marca> listar();
	public Optional<Marca> listarId(int id);
	public int guardar (Marca c);
	public void eliminar (int id);

}
