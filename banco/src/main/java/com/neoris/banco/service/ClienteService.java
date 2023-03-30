package com.neoris.banco.service;

import java.util.List;

import com.neoris.banco.entity.Cliente;

public interface ClienteService {
	public List<Cliente> getClientes();
	public Cliente getClienteByIdentificacion(String identificacion);
	public String postCliente(Cliente cliente);
	public String putCliente(Cliente cliente);
	public String deleteCliente(String identificacion);
}
