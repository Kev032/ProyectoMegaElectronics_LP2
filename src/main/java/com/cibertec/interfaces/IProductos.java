package com.cibertec.interfaces;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.cibertec.model.Productos;

@Repository
public interface IProductos extends JpaRepository<Productos, Integer>{
	
@Query("SELECT p FROM Productos p WHERE p.descripcion LIKE %?1%")
public List<Productos> findAll(String palabraClave);
}
