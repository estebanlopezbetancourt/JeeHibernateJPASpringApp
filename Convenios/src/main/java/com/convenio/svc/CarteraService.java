package com.convenio.svc;

import java.util.List;

import com.convenio.dal.entities.Cartera;

public interface CarteraService {
	
	void saveCartera(Cartera cartera);
	
	void saveCartera(List<Cartera> carteraCollection);
	 
    List<Cartera> findAllCartera();

    List<Cartera> findCarteraByRutTitular(int rut);
    
    Cartera findCarteraByRut(int rut);
 
    void updateCartera(Cartera cartera);
}
