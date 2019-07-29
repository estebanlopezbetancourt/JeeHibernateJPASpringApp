package com.convenio.dal.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.FlushMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.convenio.dal.dao.AbstractDao;
import com.convenio.dal.dao.TransaccionDao;
import com.convenio.dal.entities.Transaccion;

@Repository("transaccionDao")
public class TransaccionDaoImpl extends AbstractDao implements TransaccionDao {

	@Override
	public Transaccion GetById(int id) {
		return (Transaccion)this.getSession().get(Transaccion.class,id);
	}

	@Override
	public Transaccion GetLastTransaccionByRutBeneficiado(int rut) {
		Criteria criteria = this.getSession().createCriteria(Transaccion.class);
		criteria.add(Restrictions.eq("rutbeneficiado", rut));
		criteria.addOrder(Order.desc("fecharegistra"));
		criteria.addOrder(Order.desc("fechaconsulta"));
		return (Transaccion) criteria.list().iterator().next(); //first
	}

	@Override
	public void add(Transaccion entity) {
		this.getSession().save(entity);
	}

	@Override
	public void update(Transaccion entity) {
		this.getSession().update(entity);
	}

	@Override
	public void save(Transaccion entity) {
		this.getSession().saveOrUpdate(entity);
	}

}
