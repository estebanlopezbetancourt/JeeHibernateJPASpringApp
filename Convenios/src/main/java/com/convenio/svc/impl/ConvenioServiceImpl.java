package com.convenio.svc.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.convenio.dal.dao.*;
import com.convenio.dal.entities.*;
import com.convenio.svc.ConvenioService;
import com.convenio.svc.businesshelper.CondicionGeneralEnum;
import com.convenio.svc.businesshelper.TransactionTokenHelper;
import com.convenio.svc.dto.ConsultaResponseDto;

@Service("convenioService")
public class ConvenioServiceImpl implements ConvenioService{
	
	@Autowired
	private CondicionGeneralDao condicionGralDao;
	@Autowired
	private CarteraDao carteraDao;
	@Autowired
	private PrestacionDao prestacionDao;
	@Autowired
	private TransaccionDao transaccionDao;
	
	@Transactional
	@Override
	public ConsultaResponseDto consultaPrestaciones(int rutPersona, int rutEmpresa) {
		Date fechaConsulta = new Date();
		String tokenResult = "";
		
		//find cartera
		Cartera persona = carteraDao.GetByRutBeneficiario(rutPersona);
		
		//get all condiciones and then match condiciones with cartera properties
		List<Condiciongeneral> condicionesCollection = condicionGralDao.GetAll();
		List<Condiciongeneral> condicionesAplicanCollection = new ArrayList<Condiciongeneral>();
		for(Condiciongeneral condicion : condicionesCollection){
			CondicionGeneralEnum condicionKey = CondicionGeneralEnum.valueOf(condicion.getNombre().toUpperCase().trim().replace(" ", ""));
			
			//match between cartera properties and condiciones
			switch(condicionKey){
			    case AFILIADOSACTIVOS:
			    	if (persona.getIsactivo() && persona.getIstitular()){
			    		condicionesAplicanCollection.add(condicion);
			    	}
			    break;
			    case CARGASACTIVAS:
			    	if (persona.getIscarga() && persona.getIsactivo()){
			    		condicionesAplicanCollection.add(condicion);
			    	}
				    break;
			    case FFAA:
			    	if (persona.getIsffaa() && persona.getIsactivo()){
			    		condicionesAplicanCollection.add(condicion);
			    	}
				    break;
				default: break;
			}
		}

		//obtener prestaciones que cumplan con las condiciones reconocidas
		List<Prestacion> prestacionCollection = prestacionDao.GetByCondicionesGenerales(condicionesAplicanCollection);
		
		//registrar transacción
		Transaccion tx = new Transaccion();
		tx.setFechaconsulta(fechaConsulta);
		tx.setRutbeneficiado(rutPersona);
		tx.setRuttitular(persona.getRuttitular());
		tx.setPrestacionXML("<prestaciones><prestacion><id>1</id><desc>EjemploXML</desc></prestacion></prestaciones>");
		transaccionDao.add(tx);
		
		//create token response
		TransactionTokenHelper txTokenHelper = new TransactionTokenHelper();
		String token = txTokenHelper.CreateToken(tx);
		//return response
		ConsultaResponseDto response = new ConsultaResponseDto();
		response.setTokenTransaccion(token);
		response.setPrestaciones(prestacionCollection);
		return response;
	}

	@Transactional
	@Override
	public boolean registraUsoPrestacion(int rutPersona, int rutEmpresa, String token) {
		Date fechaRegistra = new Date();
		boolean result = false;
		// obtener identificacion de Tx partir del token
		TransactionTokenHelper txTokenHelper = new TransactionTokenHelper();
		int txId = txTokenHelper.GetTxIdFromToken(token);
		
		//obtener tx, validar que existe la tx contenida en el token
		Transaccion tx = transaccionDao.GetById(txId);
		
		//validar que la operacion se realiza dentro del tiempo de duración definido para el token
		//compare tx.FechaConsulta with tx.FechaRegistro using JodaTime
		//actualizar token
		tx.setFecharegistra(fechaRegistra);
		transaccionDao.update(tx);
		result = true;
		
		return result;
	}

}
