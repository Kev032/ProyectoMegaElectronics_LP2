package com.cibertec.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cibertec.model.Roles;

@Repository
public interface IRoles extends JpaRepository<Roles, Integer>{

}
