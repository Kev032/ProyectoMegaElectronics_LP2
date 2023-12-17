package com.cibertec.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cibertec.model.Marca;

@Repository
public interface IMarca extends JpaRepository<Marca, Integer> {

}
