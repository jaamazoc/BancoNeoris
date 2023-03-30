package com.neoris.banco.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neoris.banco.entity.Movimiento;
import com.neoris.banco.model.request.ReqReporteMovimientosDTO;
import com.neoris.banco.model.request.TransaccionDTO;
import com.neoris.banco.model.response.RespReporteMovimientosDTO;
import com.neoris.banco.service.MovimientoService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/movimientos")
public class MovimientoController {
	
	@Autowired
	private MovimientoService movimientoService;
	
	@GetMapping("")
	public ResponseEntity<List<Movimiento>> getMovimiento() {
		return new ResponseEntity<>(movimientoService.getMovimientos(), HttpStatus.OK);
	}
	
	@PostMapping("")
	public ResponseEntity<String> postMovimiento(@Validated @RequestBody TransaccionDTO transaccionDTO) {
		return new ResponseEntity<>(movimientoService.postMovimiento(transaccionDTO), HttpStatus.OK);
	}
	
	@PostMapping("/reporte")
	public ResponseEntity<List<RespReporteMovimientosDTO>> postReporteMovimiento(@Validated @RequestBody ReqReporteMovimientosDTO reqReporteMovimientosDTO) {
		return new ResponseEntity<>(movimientoService.reporteMovimientos(reqReporteMovimientosDTO), HttpStatus.OK);
	}
}
