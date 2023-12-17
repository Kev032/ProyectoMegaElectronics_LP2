package com.cibertec.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.cibertec.interfaceService.IMarcaService;
import com.cibertec.interfaces.IMarca;
import com.cibertec.model.Marca;

@Service
public class MarcaServices implements IMarcaService{
	
	@Autowired
	private IMarca data;

	@Override
	public List<Marca> listar() {
		return data.findAll(Sort.by(Sort.Direction.ASC, "idMarca"));
	}

	@Override
	public Optional<Marca> listarId(int id) {
		return data.findById(id);
	}

	@Override
	public int guardar(Marca c) {
		Marca marcaGuardada = data.save(c);
		if (marcaGuardada != null) {
			return marcaGuardada.getIdMarca(); // Devuelve el ID de la Marca guardada
		} else {
			return 0; // Retorna 0 si no se pudo guardar la Marca
		}
	}

	@Override
	public void eliminar(int id) {
		data.deleteById(id);
		
	}

}
