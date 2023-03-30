package com.neoris.banco.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RespReporteMovimientosDTO {
	
	@JsonProperty("fecha")
	private String fecha;
	
	@JsonProperty("nombre_cliente")
	private String nombreCliente;
	
	@JsonProperty("numero_cuenta")
	private String numeroCuenta;
	
	@JsonProperty("tipo_cuenta")
	private String tipoCuenta;
	
	@JsonProperty("saldo_inicial")
	private float saldoInicial;
	
	@JsonProperty("estado")
	private String estado;
	
	@JsonProperty("valor_movimiento")
	private float valorMovimiento;
	
	@JsonProperty("tipo_movimiento")
	private String tipoMovimiento;
	
	@JsonProperty("saldo_disponible")
	private float saldoDisponible;
	
}
