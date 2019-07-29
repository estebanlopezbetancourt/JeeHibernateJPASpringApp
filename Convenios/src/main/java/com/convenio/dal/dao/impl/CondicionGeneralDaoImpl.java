package com.convenio.dal.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import com.convenio.dal.dao.AbstractDao;
import com.convenio.dal.dao.CondicionGeneralDao;
import com.convenio.dal.entities.Condiciongeneral;
import com.convenio.dal.entities.Prestacion;
import com.convenio.dal.entities.PrestacionCondgeneral;

@Repository("condicionGeneralDao")
public class CondicionGeneralDaoImpl extends AbstractDao implements CondicionGeneralDao {

	@Override
	public List<Condiciongeneral> GetAll() {
		Criteria criteria = this.getSession().createCriteria(Condiciongeneral.class);
		return criteria.list();
	}

	@Override
	public List<Condiciongeneral> GetByPrestacion(int idPrestacion) {
		Prestacion entity = (Prestacion)this.getSession().get(Prestacion.class,idPrestacion);
		
		List<Condiciongeneral> condEspecificaCollection = new ArrayList<Condiciongeneral>();
		
		//iterate through relationships and get CondicionEspecifica related on each of them
		for (PrestacionCondgeneral relationalItem : entity.getPrestacionCondgenerals()) {
			condEspecificaCollection.add(relationalItem.getCondiciongeneral());
		}
		
		return condEspecificaCollection;
	}

}
