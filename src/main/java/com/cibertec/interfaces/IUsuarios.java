package com.cibertec.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cibertec.model.Usuarios;

@Repository
public interface IUsuarios extends JpaRepository<Usuarios, Integer>{

}
