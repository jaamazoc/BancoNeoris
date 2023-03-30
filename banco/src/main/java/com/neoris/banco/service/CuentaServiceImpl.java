package com.neoris.banco.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.neoris.banco.entity.Cliente;
import com.neoris.banco.entity.Cuenta;
import com.neoris.banco.entity.Persona;
import com.neoris.banco.exception.CustomException;
import com.neoris.banco.model.request.ReqCuentaDTO;
import com.neoris.banco.repository.ClienteRepository;
import com.neoris.banco.repository.CuentaRepository;
import com.neoris.banco.repository.PersonaRepository;

@Service
public class CuentaServiceImpl implements CuentaService {
	
	private CuentaRepository cuentaRepository;
	private PersonaRepository personaRepository;
	private ClienteRepository clienteRepository;
	
	private static final Logger logger = LoggerFactory.getLogger(CuentaServiceImpl.class);
	private static final Gson gson = new Gson();
	
	@Autowired
	public CuentaServiceImpl(CuentaRepository cuentaRepository, PersonaRepository personaRepository, ClienteRepository clienteRepository) {
		this.cuentaRepository = cuentaRepository;
		this.personaRepository = personaRepository;
		this.clienteRepository = clienteRepository;
	}

	@Override
	public List<Cuenta> getCuentas() {
		logger.info("Inicio método getCuentas");
		List<Cuenta> cuentas = cuentaRepository.findAll();
		if(cuentas == null || cuentas.size() == 0) {
			throw new CustomException("Lista de cuentas vacía", HttpStatus.NOT_FOUND);
		}
		return cuentas;
	}
	
	@Override
	public Cuenta getCuentaByNumeroCuenta(String numeroCuenta) {
		logger.info("Inicio método getCuentaByNumeroCuenta");
		Cuenta cuenta = cuentaRepository.findByNumeroCuenta(numeroCuenta);
		if(cuenta == null) {
			throw new CustomException("Cuenta no encontrada", HttpStatus.NOT_FOUND);
		}
		logger.info("retorna cuenta: " + gson.toJson(cuenta));
		return cuenta;
	}

	@Override
	public String postCuenta(ReqCuentaDTO reqCuentaDTO) {
		logger.info("Inicio método postCuenta");
		Persona persona = personaRepository.findByNumId(reqCuentaDTO.getNumeroIdentificacion());
		if(persona == null) {
			throw new CustomException("Persona no encontrada", HttpStatus.NOT_FOUND);
		}
		Cliente cliente = clienteRepository.findByPersona(persona);
		if(cliente == null) {
			throw new CustomException("Cliente no encontrado", HttpStatus.NOT_FOUND);
		}
		Cuenta cuenta = new Cuenta();
		cuenta.setCliente(cliente);
		cuenta.setEstado(true);
		cuenta.setNumeroCuenta(reqCuentaDTO.getNumeroCuenta());
		cuenta.setSaldo(reqCuentaDTO.getSaldo());
		cuenta.setTipoCuenta(reqCuentaDTO.getTipoCuenta());
		cuentaRepository.saveAndFlush(cuenta);
		return "Cuenta guardada exitosamente";
	}

	@Override
	public String putCuenta(ReqCuentaDTO reqCuentaDTO) {
		logger.info("Inicio método putCuenta");
		Cuenta cuentaExistente = cuentaRepository.findByNumeroCuenta(reqCuentaDTO.getNumeroCuenta());
		if(cuentaExistente == null) {
			throw new CustomException("Cuenta no encontrada", HttpStatus.NOT_FOUND);
		}
		logger.info("Cuenta encontrada");
		cuentaExistente.setEstado(reqCuentaDTO.isEstado());
		cuentaExistente.setSaldo(reqCuentaDTO.getSaldo());
		cuentaExistente.setTipoCuenta(reqCuentaDTO.getTipoCuenta());
		cuentaRepository.saveAndFlush(cuentaExistente);
		return "Cuenta actualizada";
	}

	@Override
	public String deleteCuentaByNumeroCuenta(String numeroCuenta) {
		logger.info("Inicio método deleteCuentaByNumeroCuenta");
		Cuenta cuenta = cuentaRepository.findByNumeroCuenta(numeroCuenta);
		if(cuenta == null) {
			throw new CustomException("Cuenta no encontrada", HttpStatus.NOT_FOUND);
		}
		cuentaRepository.delete(cuenta);
		return "Cuenta borrada exitosamente";
	}
	
}
