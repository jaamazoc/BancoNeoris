package com.neoris.banco.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neoris.banco.entity.Cliente;
import com.neoris.banco.service.ClienteService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/clientes")
public class ClienteController {
	
	@Autowired
	private ClienteService clienteService;
	
	@GetMapping("")
	public ResponseEntity<List<Cliente>> getClientes() {
		return new ResponseEntity<>(clienteService.getClientes(), HttpStatus.OK);
	}
	
	@GetMapping("/{numId}")
	public ResponseEntity<Cliente> getCliente(@PathVariable String numId) {
		return new ResponseEntity<>(clienteService.getClienteByIdentificacion(numId), HttpStatus.OK);
	}
	
	@PostMapping("")
	public ResponseEntity<String> postCliente(@Validated @RequestBody Cliente cliente) {
		return new ResponseEntity<>(clienteService.postCliente(cliente), HttpStatus.OK);
	}
	
	@PutMapping("")
	public ResponseEntity<String> putCliente(@Validated @RequestBody Cliente cliente) {
		return new ResponseEntity<>(clienteService.putCliente(cliente), HttpStatus.OK);
	}
	
	@DeleteMapping("/{numId}")
	public ResponseEntity<String> deleteCliente(@PathVariable String numId) {
		return new ResponseEntity<>(clienteService.deleteCliente(numId), HttpStatus.OK);
	}
}
