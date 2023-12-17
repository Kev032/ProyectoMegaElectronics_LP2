package com.cibertec.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cibertec.model.UsuarioRol;

@Repository
public interface IUsuarioRol extends JpaRepository<UsuarioRol, Integer>{

}
