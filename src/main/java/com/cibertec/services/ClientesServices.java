package com.cibertec.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.cibertec.interfaceService.IClienteService;
import com.cibertec.interfaces.IClientes;
import com.cibertec.model.Clientes;

@Service
public class ClientesServices implements IClienteService{
	
	@Autowired
	private IClientes data;

	@Override
	public List<Clientes> listar() {
		return data.findAll(Sort.by(Sort.Direction.ASC, "idCliente"));
	}

	@Override
	public Optional<Clientes> listarId(int id) {
		return data.findById(id);
	}

	@Override
	public int guardar(Clientes c) {
		Clientes cliente = data.save(c);
		if (cliente != null) {
			return cliente.getIdCliente(); // Devuelve el ID del cliente guardada
		} else {
			return 0; // Retorna 0 si no se pudo guardar el cliente
		}
	}

	@Override
	public void eliminar(int id) {
		data.deleteById(id);
		
	}

	@Override
	public List<Clientes> listaCliente(String palabraClave) {
		if(palabraClave != null) {
    		return data.findAll(palabraClave);
    	}
		return data.findAll();
	}

}
