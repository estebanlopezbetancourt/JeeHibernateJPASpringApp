package com.convenio.dal.dao.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.convenio.dal.dao.AbstractDao;
import com.convenio.dal.dao.PrestacionDao;
import com.convenio.dal.entities.Condiciongeneral;
import com.convenio.dal.entities.Prestacion;
import com.convenio.dal.entities.PrestacionCondgeneral;

@Repository("prestacionDao")
public class PrestacionDaoImpl extends AbstractDao implements PrestacionDao {

	@Override
	public List<Prestacion> GetAll() {
		Criteria criteria = this.getSession().createCriteria(Prestacion.class);
		return criteria.list();
	}

	@Override
	public List<Prestacion> GetByObjectFilter(Prestacion entity) {
		Criteria criteria = this.getSession().createCriteria(Prestacion.class);
		criteria.add(Example.create(entity)
				.ignoreCase()
				.enableLike()
				.excludeZeroes());
		
		return criteria.list();
	}

	@Override
	public List<Prestacion> GetByCondicionesGenerales(List<Condiciongeneral> condiciones) {
		Criteria criteria = this.getSession().createCriteria(PrestacionCondgeneral.class);
		
		//get relations where idCondicion it is contains in input parameter
		List<Integer> idCondiciones = new ArrayList<Integer>(); 
		for (Condiciongeneral item : condiciones){
			idCondiciones.add(item.getId());
		}
		criteria.createAlias("condiciongeneral", "cg");
		criteria.add(Restrictions.in("cg.id", idCondiciones));
		
		List<PrestacionCondgeneral> relacionesCollection = criteria.list();
		
		//get prestaciones where his Id it is contains in PrestacionCondgeneral result
		List<Prestacion> prestacionCollectionResult = new ArrayList<Prestacion>();
		
		if (relacionesCollection != null && relacionesCollection.size() > 0){
			for (PrestacionCondgeneral item : relacionesCollection){
				Prestacion prestacion = item.getPrestacion();
				prestacionCollectionResult.add(prestacion);
			}
		}		
				
		return prestacionCollectionResult;
	}

}
