package com.convenio.svc;

import java.util.List;

import com.convenio.dal.entities.Cartera;
import com.convenio.dal.entities.Prestacion;
import com.convenio.svc.dto.ConsultaResponseDto;

public interface ConvenioService {
	ConsultaResponseDto consultaPrestaciones(int rutPersona, int rutEmpresa);
	
	boolean registraUsoPrestacion(int rutPersona, int rutEmpresa, String token);
}
