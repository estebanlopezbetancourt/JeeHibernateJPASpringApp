package com.convenio.svc.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.convenio.dal.dao.CarteraDao;
import com.convenio.dal.entities.Cartera;
import com.convenio.dal.entities.PrestacionCondespecifica;
import com.convenio.svc.CarteraService;

@Service("carteraService")
@Transactional
public class CarteraServiceImpl implements CarteraService{
	 
	@Autowired
	private CarteraDao dao;

	@Override
	public void saveCartera(Cartera cartera) {
		dao.add(cartera);
	}

	@Override
	public List<Cartera> findAllCartera() {
		return dao.GetAll();
	}

	@Override
	public List<Cartera> findCarteraByRutTitular(int rut) {
		return dao.GetByRutTitular(rut);
	}

	@Override
	public Cartera findCarteraByRut(int rut) {
		return dao.GetByRutBeneficiario(rut);
	}

	@Override
	public void updateCartera(Cartera cartera) {
		dao.update(cartera);
		
	}

	@Override
	public void saveCartera(List<Cartera> carteraCollection) {
		for (Cartera item : carteraCollection) {
			dao.save(item);
		}
	}
	
	

}
