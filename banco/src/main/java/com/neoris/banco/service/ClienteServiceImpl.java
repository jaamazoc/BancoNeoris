package com.neoris.banco.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.neoris.banco.entity.Cliente;
import com.neoris.banco.entity.Persona;
import com.neoris.banco.exception.CustomException;
import com.neoris.banco.repository.ClienteRepository;
import com.neoris.banco.repository.PersonaRepository;

@Service
public class ClienteServiceImpl implements ClienteService{
	
	private ClienteRepository clienteRepository;
	private PersonaRepository personaRepository;
	
	private static final Logger logger = LoggerFactory.getLogger(ClienteServiceImpl.class);
	private static final Gson gson = new Gson();
	
	@Autowired
	public ClienteServiceImpl(ClienteRepository clienteRepository, PersonaRepository personaRepository) {
		this.clienteRepository = clienteRepository;
		this.personaRepository = personaRepository;
	}

	@Override
	public List<Cliente> getClientes() {
		logger.info("Inicio método getClientes");
		List<Cliente> clientes = clienteRepository.findAll();
		if(clientes == null || clientes.size() == 0) {
			throw new CustomException("Lista de clientes vacía", HttpStatus.NOT_FOUND);
		}
		return clientes;
	}
	
	@Override
	public Cliente getClienteByIdentificacion(String numId) {
		logger.info("Inicio método getClienteByIdentificacion");
		Persona persona = personaRepository.findByNumId(numId);
		if(persona == null) {
			throw new CustomException("Persona no encontrada", HttpStatus.NOT_FOUND);
		}
		logger.info("Persona encontrada");
		Cliente cliente = clienteRepository.findByPersona(persona);
		if(cliente == null) {
			throw new CustomException("Cliente no encontrado", HttpStatus.NOT_FOUND);
		}
		logger.info("retorna cliente: " + gson.toJson(cliente));
		return cliente;
	}

	@Override
	public String postCliente(Cliente cliente) {
		logger.info("Inicio método postCliente");
		personaRepository.saveAndFlush(cliente.getPersona());
		logger.info("Persona guardada");
		clienteRepository.saveAndFlush(cliente);
		logger.info("Cliente guardado");
		return "Cliente guardado exitosamente";
	}

	@Override
	public String putCliente(Cliente cliente) {
		logger.info("Inicio método putCliente");
		Persona persona = personaRepository.findByNumId(cliente.getPersona().getNumId());
		if(persona == null) {
			throw new CustomException("Persona no encontrada", HttpStatus.NOT_FOUND);
		}
		logger.info("persona encontrada");
		Cliente clienteExistente = clienteRepository.findByPersona(persona);
		if(clienteExistente == null) {
			throw new CustomException("Cliente no encontrado", HttpStatus.NOT_FOUND);
		}
		logger.info("cliente encontrado");
		persona.setDireccion(cliente.getPersona().getDireccion());
		persona.setEdad(cliente.getPersona().getEdad());
		persona.setGenero(cliente.getPersona().getGenero());
		persona.setNumId(cliente.getPersona().getNumId());
		persona.setNombre(cliente.getPersona().getNombre());
		persona.setTelefono(cliente.getPersona().getTelefono());
		personaRepository.saveAndFlush(persona);
		logger.info("persona actualizada");
		clienteExistente.setContraseña(cliente.getContraseña());
		clienteExistente.setEstado(cliente.getEstado());
		clienteExistente.setPersona(persona);
		clienteRepository.saveAndFlush(clienteExistente);
		return "Cliente actualizado exitosamente";
	}

	@Override
	public String deleteCliente(String numId) {
		logger.info("Inicio método deleteCliente");
		Persona persona = personaRepository.findByNumId(numId);
		if(persona == null) {
			throw new CustomException("Persona no encontrada", HttpStatus.NOT_FOUND);
		}
		Cliente cliente = clienteRepository.findByPersona(persona);
		if(cliente == null) {
			throw new CustomException("Cliente no encontrado", HttpStatus.NOT_FOUND);
		}
		clienteRepository.delete(cliente);
		personaRepository.delete(persona);
		return "Cliente borrado exitosamente";
	}

}
