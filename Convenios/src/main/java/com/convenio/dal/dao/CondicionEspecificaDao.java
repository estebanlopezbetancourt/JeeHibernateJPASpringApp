package com.convenio.dal.dao;

import java.util.List;

import com.convenio.dal.entities.Condicionespecifica;

public interface CondicionEspecificaDao {
	
	public List<Condicionespecifica> GetAll();
	
	public List<Condicionespecifica> GetByPrestacion(int idPrestacion);

}
