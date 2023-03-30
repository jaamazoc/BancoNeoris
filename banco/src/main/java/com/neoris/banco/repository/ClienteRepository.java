package com.neoris.banco.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.neoris.banco.entity.Cliente;
import com.neoris.banco.entity.Persona;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

	public Cliente findByPersona(Persona persona);
}
