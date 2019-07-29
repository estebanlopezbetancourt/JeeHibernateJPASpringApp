package com.convenio.dal.dao;

import java.util.List;

import com.convenio.dal.entities.Condiciongeneral;;

public interface CondicionGeneralDao {
	
	public List<Condiciongeneral> GetAll();
	
	public List<Condiciongeneral> GetByPrestacion(int idPrestacion);
}
