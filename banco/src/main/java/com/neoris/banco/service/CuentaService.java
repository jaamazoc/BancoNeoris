package com.neoris.banco.service;

import java.util.List;

import com.neoris.banco.entity.Cuenta;
import com.neoris.banco.model.request.ReqCuentaDTO;

public interface CuentaService {
	public List<Cuenta> getCuentas();
	public Cuenta getCuentaByNumeroCuenta(String numeroCuenta);
	public String postCuenta(ReqCuentaDTO reqCuentaDTO);
	public String putCuenta(ReqCuentaDTO reqCuentaDTO);
	public String deleteCuentaByNumeroCuenta(String numeroCuenta);
}
