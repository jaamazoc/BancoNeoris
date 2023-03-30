package com.neoris.banco.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransaccionDTO {
	
	@JsonProperty("numero_cuenta")
	private String numeroCuenta;
	
	@JsonProperty("tipo_cuenta")
	private String tipoCuenta;

	@JsonProperty("tipo_movimiento")
	private String tipoMovimiento;
	
	private float valor;
	
}
