package com.convenio.dal.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import com.convenio.dal.dao.AbstractDao;
import com.convenio.dal.dao.CondicionEspecificaDao;
import com.convenio.dal.entities.Condicionespecifica;
import com.convenio.dal.entities.Prestacion;
import com.convenio.dal.entities.PrestacionCondespecifica;

@Repository("condicionEspecificaDao")
public class CondicionEspecificaDaoImpl extends AbstractDao implements CondicionEspecificaDao {

	@Override
	public List<Condicionespecifica> GetAll() {
		Criteria criteria = this.getSession().createCriteria(Condicionespecifica.class);
		return criteria.list();
	}

	@Override
	public List<Condicionespecifica> GetByPrestacion(int idPrestacion) {
		Prestacion entity = (Prestacion)this.getSession().get(Prestacion.class,idPrestacion);
		
		List<Condicionespecifica> condEspecificaCollection = new ArrayList<Condicionespecifica>();
		
		//iterate through relationships and get CondicionEspecifica related on each of them
		for (PrestacionCondespecifica relationalItem : entity.getPrestacionCondespecificas()) {
			condEspecificaCollection.add(relationalItem.getCondicionespecifica());
		}
		
		return condEspecificaCollection;
		
	}

}
