package com.cibertec.interfaceService;

import java.util.List;
import java.util.Optional;

import com.cibertec.model.Clientes;

public interface IClienteService {
	
	public List<Clientes> listar();
	public Optional<Clientes> listarId(int id);
	public int guardar (Clientes c);
	public void eliminar (int id);
	// Filtro Consulta
	List<Clientes> listaCliente(String palabraClave);

}
