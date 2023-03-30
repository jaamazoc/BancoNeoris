package com.neoris.banco.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.neoris.banco.entity.Persona;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Integer> {
	
	public Persona findByNombreAndNumId(String nombre, String numId);
	public Persona findByNumId(String numId);
}
