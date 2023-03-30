package com.neoris.banco.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.neoris.banco.entity.Cliente;
import com.neoris.banco.entity.Cuenta;
import com.neoris.banco.entity.Movimiento;
import com.neoris.banco.entity.Persona;
import com.neoris.banco.exception.CustomException;
import com.neoris.banco.model.request.ReqReporteMovimientosDTO;
import com.neoris.banco.model.request.TransaccionDTO;
import com.neoris.banco.model.response.RespReporteMovimientosDTO;
import com.neoris.banco.repository.ClienteRepository;
import com.neoris.banco.repository.CuentaRepository;
import com.neoris.banco.repository.MovimientoRepository;
import com.neoris.banco.repository.PersonaRepository;

@Service
public class MovimientoServiceImpl implements MovimientoService {
	
	private MovimientoRepository movimientoRepository;
	private CuentaRepository cuentaRepository;
	private ClienteRepository clienteRepository;
	private PersonaRepository personaRepository;
	
	private static final Logger logger = LoggerFactory.getLogger(MovimientoServiceImpl.class);
	private static final Gson gson = new Gson();
	
	@Autowired
	public MovimientoServiceImpl(MovimientoRepository movimientoRepository, CuentaRepository cuentaRepository, ClienteRepository clienteRepository, PersonaRepository personaRepository) {
		this.movimientoRepository = movimientoRepository;
		this.cuentaRepository = cuentaRepository;
		this.clienteRepository = clienteRepository;
		this.personaRepository = personaRepository;
	}

	@Override
	public List<Movimiento> getMovimientos() {
		logger.info("Inicio método getMovimientos");
		List<Movimiento> movimientos = movimientoRepository.findAll();
		if(movimientos == null || movimientos.size() == 0) {
			throw new CustomException("Lista de movimientos vacía", HttpStatus.NOT_FOUND);
		}
		return movimientos;
	}

	@Override
	public String postMovimiento(TransaccionDTO transaccionDTO) {
		logger.info("Inicio método postMovimiento: " + gson.toJson(transaccionDTO));
		Movimiento movimiento = new Movimiento();
		Cuenta cuenta = cuentaRepository.findByNumeroCuenta(transaccionDTO.getNumeroCuenta());
		if(cuenta == null) {
			throw new CustomException("Cuenta no encontrada", HttpStatus.NOT_FOUND);
		}
		if(!cuenta.isEstado()) {
			throw new CustomException("La cuenta a la que hace referencia está inactiva", HttpStatus.UNPROCESSABLE_ENTITY);
		}
		if(!cuenta.getTipoCuenta().equalsIgnoreCase(transaccionDTO.getTipoCuenta())) {
			throw new CustomException("Cuenta no encontrada", HttpStatus.UNPROCESSABLE_ENTITY);
		}
		float saldoActual = cuenta.getSaldo();
		if(transaccionDTO.getTipoMovimiento().equalsIgnoreCase("RETIRO")) {
			if(saldoActual < transaccionDTO.getValor()) {
				throw new CustomException("Saldo no disponible", HttpStatus.UNPROCESSABLE_ENTITY);
			}
			float nuevoSaldo = saldoActual - transaccionDTO.getValor();
			logger.info("nuevo saldo después de retiro: " + nuevoSaldo);
			cuenta.setSaldo(nuevoSaldo); 
		} else if (transaccionDTO.getTipoMovimiento().equalsIgnoreCase("DEPOSITO")) {
			float nuevoSaldo = saldoActual + transaccionDTO.getValor();
			logger.info("nuevo saldo después de depósito: " + nuevoSaldo);
			cuenta.setSaldo(nuevoSaldo);
		} else {
			throw new CustomException("El tipo de movimiento no existe", HttpStatus.UNPROCESSABLE_ENTITY);
		}
		logger.info("Cuenta a actualizar: " + gson.toJson(cuenta));
		cuentaRepository.saveAndFlush(cuenta);
		movimiento.setSaldo(saldoActual);
		movimiento.setCuenta(cuenta);
		movimiento.setFecha(new java.sql.Date(System.currentTimeMillis()));
		movimiento.setTipo(transaccionDTO.getTipoMovimiento());
		movimiento.setValor(transaccionDTO.getValor());
		logger.info("Movimiento a guardar: " + gson.toJson(movimiento));
		movimientoRepository.saveAndFlush(movimiento);
		return "Movimiento registrado exitosamente";
	}

	@Override
	public List<RespReporteMovimientosDTO> reporteMovimientos(ReqReporteMovimientosDTO reporteMovimientosDTO) {
		logger.info("Inicio método reporteMovimientos: " + gson.toJson(reporteMovimientosDTO));
		List<RespReporteMovimientosDTO> listaReporte = new ArrayList<>();
		Persona persona = personaRepository.findByNombreAndNumId(reporteMovimientosDTO.getNombreCliente(), reporteMovimientosDTO.getNumeroIdentificacion());
		if(persona == null) {
			throw new CustomException("Persona no encontrada", HttpStatus.NOT_FOUND);
		}
		Cliente cliente = clienteRepository.findByPersona(persona);
		if(cliente == null) {
			throw new CustomException("Cliente no encontrado", HttpStatus.NOT_FOUND);
		}
		List<Cuenta> listaCuentas = cuentaRepository.findByCliente(cliente);
		if(listaCuentas == null || listaCuentas.size() == 0) {
			throw new CustomException("Lista de cuentas vacía", HttpStatus.NOT_FOUND);
		}
		for(Cuenta cuenta : listaCuentas) {
			logger.info("iteración cuenta");
			List<Movimiento> listaMovimientos = movimientoRepository.findByCuentaOrderByFecha(cuenta);
			if(listaMovimientos != null && listaMovimientos.size() > 0) {
				for(Movimiento m : listaMovimientos) {
					logger.info("iteración movimiento");
					RespReporteMovimientosDTO reporte = new RespReporteMovimientosDTO();
					reporte.setFecha(m.getFecha().toString());
					reporte.setNombreCliente(persona.getNombre());
					reporte.setEstado(cuenta.isEstado()?"activa":"inactiva");
					reporte.setNumeroCuenta(cuenta.getNumeroCuenta());
					reporte.setSaldoInicial(m.getSaldo());
					reporte.setTipoCuenta(cuenta.getTipoCuenta());
					reporte.setTipoMovimiento(m.getTipo());
					reporte.setValorMovimiento(m.getValor());
					float saldoDisponible = 0;
					if(m.getTipo().equalsIgnoreCase("RETIRO")) {
						saldoDisponible = m.getSaldo() - m.getValor();
					} else {
						saldoDisponible = m.getSaldo() + m.getValor();
					}
					reporte.setSaldoDisponible(saldoDisponible);
					logger.info("se agrega reporte: " + gson.toJson(reporte));
					listaReporte.add(reporte);
				}
			}
		}
		if(listaReporte == null || listaReporte.size() < 1) {
			throw new CustomException("Lista de movimientos vacía para este cliente", HttpStatus.NOT_FOUND);
		}
		logger.info("Reporte a generar: " + gson.toJson(listaReporte));
		return listaReporte;
	}

}
