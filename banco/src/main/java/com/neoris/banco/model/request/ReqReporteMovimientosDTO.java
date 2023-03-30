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
public class ReqReporteMovimientosDTO {
	
	@JsonProperty("nombre_cliente")
	private String nombreCliente;
	
	@JsonProperty("numero_identificacion")
	private String numeroIdentificacion;
	
}
