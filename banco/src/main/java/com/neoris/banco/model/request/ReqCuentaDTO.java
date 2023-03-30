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
public class ReqCuentaDTO {
	
	@JsonProperty(value =  "numero_identificacion", required = false)
	private String numeroIdentificacion;
	@JsonProperty("numero_cuenta")
	private String numeroCuenta;
	@JsonProperty("tipo_cuenta")
	String tipoCuenta;
	float saldo;
	boolean estado;
}
