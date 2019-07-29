package com.convenio.dal.dao;

import java.util.List;
import java.util.Set;

import com.convenio.dal.entities.Condiciongeneral;
import com.convenio.dal.entities.Prestacion;

public interface PrestacionDao {
	
	public List<Prestacion> GetAll();
	
	//get all prestacion where rut titular can apply following condiciones generales
	//public List<Prestacion> GetByRutTitular(int rut);
	
	//public List<Prestacion> GetByCondicionGeneral(HashSet<int> idCondicionesCollection);
	
	public List<Prestacion> GetByObjectFilter(Prestacion entity);
	
	public List<Prestacion> GetByCondicionesGenerales(List<Condiciongeneral> condiciones);
}


