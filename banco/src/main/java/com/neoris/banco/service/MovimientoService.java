package com.neoris.banco.service;

import java.util.List;

import com.neoris.banco.entity.Movimiento;
import com.neoris.banco.model.request.ReqReporteMovimientosDTO;
import com.neoris.banco.model.request.TransaccionDTO;
import com.neoris.banco.model.response.RespReporteMovimientosDTO;

public interface MovimientoService {
	public List<Movimiento> getMovimientos();
	public String postMovimiento(TransaccionDTO transaccionDTO);
	public List<RespReporteMovimientosDTO> reporteMovimientos(ReqReporteMovimientosDTO reporteMovimientosDTO);
}
