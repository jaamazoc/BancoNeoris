package com.neoris.banco.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.neoris.banco.entity.Cuenta;
import com.neoris.banco.entity.Movimiento;

@Repository
public interface MovimientoRepository extends JpaRepository<Movimiento, Integer> {
	public List<Movimiento> findByCuenta(Cuenta cuenta);
	
	@Query(value = "SELECT m FROM Movimiento m where m.cuenta = ?1 ORDER BY m.fecha ASC")
	public List<Movimiento> findByCuentaOrderByFecha(Cuenta cuenta);
}
