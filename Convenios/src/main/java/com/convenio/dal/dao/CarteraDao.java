package com.convenio.dal.dao;

import java.util.List;

import com.convenio.dal.entities.Cartera;
import com.convenio.dal.entities.Transaccion;

public interface CarteraDao {
	
	public List<Cartera> GetAll();
	
	public Cartera GetByRutBeneficiario(int rut);
	
	public List<Cartera> GetByRutTitular(int rut);
	
	public List<Cartera> GetByObjectFilter(Cartera entity);
	
	void add(Cartera entity);
	
	void update(Cartera entity);
	
	// if doesn't exist it will be created, otherwise will be updated
	void save(Cartera entity);
	
}
