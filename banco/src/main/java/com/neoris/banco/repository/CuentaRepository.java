package com.neoris.banco.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.neoris.banco.entity.Cliente;
import com.neoris.banco.entity.Cuenta;

@Repository
public interface CuentaRepository extends JpaRepository<Cuenta, Integer> {
	public Cuenta findByNumeroCuenta(String numeroCuenta);
	public List<Cuenta> findByCliente(Cliente cliente);
}
