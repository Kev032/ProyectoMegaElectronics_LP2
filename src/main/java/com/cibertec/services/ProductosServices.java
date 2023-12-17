package com.cibertec.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.cibertec.interfaceService.IProductoService;
import com.cibertec.interfaces.IProductos;
import com.cibertec.model.Productos;

@Service
public class ProductosServices implements IProductoService {
	
	@Autowired
	private IProductos data;

	@Override
	public List<Productos> listar() {
		return data.findAll(Sort.by(Sort.Direction.ASC, "idProducto"));
	}

	@Override
	public Optional<Productos> listarId(int id) {
		return data.findById(id);
	}

	@Override
	public int guardar(Productos p) {
		Productos productos = data.save(p);
		if (productos != null) {
			return productos.getIdProducto(); // Devuelve el ID del producto guardada
		} else {
			return 0; // Retorna 0 si no se pudo guardar el producto
		}
	}

	@Override
	public void eliminar(int id) {
		data.deleteById(id);
		
	}

	@Override
	public List<Productos> listaProducto(String palabraClave) {
		if(palabraClave != null) {
    		return data.findAll(palabraClave);
    	}
		return data.findAll();
		
	}

}