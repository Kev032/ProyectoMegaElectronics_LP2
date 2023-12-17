package com.cibertec.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cibertec.model.Categorias;

@Repository
public interface ICategorias extends JpaRepository<Categorias, Integer> {

}
