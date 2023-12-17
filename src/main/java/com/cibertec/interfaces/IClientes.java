package com.cibertec.interfaces;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cibertec.model.Clientes;

@Repository
public interface IClientes extends JpaRepository<Clientes, Integer> {
	
	@Query("SELECT c FROM Clientes c WHERE c.nombre LIKE %?1%")
	public List<Clientes> findAll(String palabraClave);
}
