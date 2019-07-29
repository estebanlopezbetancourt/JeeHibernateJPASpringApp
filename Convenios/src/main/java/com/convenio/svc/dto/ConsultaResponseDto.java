package com.convenio.svc.dto;

import java.util.List;

import com.convenio.dal.entities.Prestacion;


public class ConsultaResponseDto {
	
	private List<Prestacion> prestaciones;
	private String tokenTransaccion;
	
	public List<Prestacion> getPrestaciones() {
		return prestaciones;
	}
	public void setPrestaciones(List<Prestacion> prestaciones) {
		this.prestaciones = prestaciones;
	}
	public String getTokenTransaccion() {
		return tokenTransaccion;
	}
	public void setTokenTransaccion(String tokenTransaccion) {
		this.tokenTransaccion = tokenTransaccion;
	} 
	
}
