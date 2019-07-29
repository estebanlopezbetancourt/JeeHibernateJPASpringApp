package com.convenio.dal.dao;

import com.convenio.dal.entities.Transaccion;

public interface TransaccionDao {
	
	Transaccion GetById(int id);
	
	public Transaccion GetLastTransaccionByRutBeneficiado(int rut);
	
	void add(Transaccion entity);
	
	void update(Transaccion entity);
	
	// if doesn't exist it will be created, otherwise will be updated
	void save(Transaccion entity);
	
}
