package com.convenio.dal.dao.impl;


import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Restrictions;

import org.springframework.stereotype.Repository;

import com.convenio.dal.dao.AbstractDao;
import com.convenio.dal.dao.CarteraDao;
import com.convenio.dal.entities.Cartera;

@Repository("carteraDao")
public class CarteraDaoImpl extends AbstractDao implements CarteraDao {

	@Override
	public List<Cartera> GetAll() {
		Criteria criteria = this.getSession().createCriteria(Cartera.class);
		return criteria.list();
	}

	@Override
	public Cartera GetByRutBeneficiario(int rut) {
		Criteria criteria = this.getSession().createCriteria(Cartera.class);
		criteria.add(Restrictions.eq("rut", rut));
		return (Cartera) criteria.list().iterator().next(); //first
	}

	@Override
	public List<Cartera> GetByRutTitular(int rut) {
		Criteria criteria = this.getSession().createCriteria(Cartera.class);
		criteria.add(Restrictions.eq("ruttitular", rut));
		return criteria.list();
	}

	@Override
	public List<Cartera> GetByObjectFilter(Cartera entity) {
		Criteria criteria = this.getSession().createCriteria(Cartera.class);
		criteria.add(Example.create(entity)
				.ignoreCase()
				.enableLike()
				.excludeZeroes());
		
		return criteria.list();
	}

	@Override
	public void add(Cartera entity) {
		this.getSession().save(entity);
	}

	@Override
	public void update(Cartera entity) {
		this.getSession().update(entity);
	}

	@Override
	public void save(Cartera entity) {
		this.getSession().saveOrUpdate(entity);
	}

}
