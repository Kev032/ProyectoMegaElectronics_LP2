package com.cibertec.interfaceService;

import java.util.List;
import java.util.Optional;
import com.cibertec.model.Productos;



public interface IProductoService {
	
	public List<Productos> listar();
	public Optional<Productos> listarId(int id);
	public int guardar (Productos c);
	public void eliminar (int id);
	// Filtro Consulta
	List<Productos> listaProducto(String palabraClave);
}