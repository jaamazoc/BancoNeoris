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

import com.neoris.banco.entity.Cuenta;
import com.neoris.banco.model.request.ReqCuentaDTO;
import com.neoris.banco.service.CuentaService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/cuentas")
public class CuentaController {
	
	@Autowired
	private CuentaService cuentaService;
	
	@GetMapping("")
	public ResponseEntity<List<Cuenta>> getCuentas() {
		return new ResponseEntity<>(cuentaService.getCuentas(), HttpStatus.OK);
	}
	
	@GetMapping("/{numCuenta}")
	public ResponseEntity<Cuenta> getCuenta(@PathVariable String numCuenta) {
		return new ResponseEntity<>(cuentaService.getCuentaByNumeroCuenta(numCuenta), HttpStatus.OK);
	}
	
	@PostMapping("")
	public ResponseEntity<String> postCuenta(@Validated @RequestBody ReqCuentaDTO reqCuentaDTO) {
		return new ResponseEntity<>(cuentaService.postCuenta(reqCuentaDTO), HttpStatus.OK);
	}
	
	@PutMapping("")
	public ResponseEntity<String> putCuenta(@Validated @RequestBody ReqCuentaDTO reqCuentaDTO) {
		return new ResponseEntity<>(cuentaService.putCuenta(reqCuentaDTO), HttpStatus.OK);
	}
	
	@DeleteMapping("/{numCuenta}")
	public ResponseEntity<String> deleteCuenta(@PathVariable String numCuenta) {
		return new ResponseEntity<>(cuentaService.deleteCuentaByNumeroCuenta(numCuenta), HttpStatus.OK);
	}
}
