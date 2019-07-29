

import java.beans.Expression;
import java.util.ArrayList;
import java.util.List;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.convenio.dal.entities.*;
import com.convenio.dal.dao.*;
import com.convenio.svc.CarteraService;
import com.convenio.svc.ConvenioService;
import com.convenio.svc.dto.ConsultaResponseDto;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import org.springframework.transaction.annotation.Transactional;

public class App{
	
	public static void main(String[] args) {
		System.out.println("Maven + Hibernate + Oracle");
		
		ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");

		ConvenioService service = (ConvenioService) context.getBean("convenioService");
		
		int rutPersona = 16367363;
		int rutEmpresa = 22333444;
		ConsultaResponseDto result = service.consultaPrestaciones(rutPersona, rutEmpresa);
		String token = result.getTokenTransaccion();
		service.registraUsoPrestacion(rutPersona, rutEmpresa, token);

		//TEST SPRING SVC + DAL
		//CarteraService service = (CarteraService) context.getBean("carteraService");
		//SvcTest(service);
		
		//TEST HIBERNATE ANTES INTEGRACION CON SPRING FWK
		//App.GuardarTest(session); // passed
		//App.GetByFilter(session);
		//App.GetById(session);
		//App.GetWithChildRelation(session);
		//App.GetByFilterExampleObject(session); 
		
	}
	
	@Transactional
	private static void RunDaoTest(){
		com.convenio.dal.dao.PrestacionDao prestacionDao = (PrestacionDao) new com.convenio.dal.dao.impl.PrestacionDaoImpl();
		List<Prestacion> prestacionCollection = prestacionDao.GetAll();
				
	}
	private static void GuardarTest(Session session){
		Cartera entity = new Cartera();
		
		session.beginTransaction();
		
		entity.setNombre("Marcelo Lopez");
		entity.setRut(14567876);
		entity.setRuttitular(16367363);
		entity.setIstitular(false);
		entity.setIsactivo(false);
		entity.setIscarga(true);
		entity.setIsffaa(false);
		
		System.out.println("before - ID: " + entity.getId());
		session.save(entity);
		session.getTransaction().commit();
		
		System.out.println("after - ID: " + entity.getId());
	}
	
	private static void SvcTest(CarteraService service){
		Cartera entity = new Cartera();
		entity.setNombre("Marcelo Lopez");
		entity.setRut(14567876);
		entity.setRuttitular(16367363);
		entity.setIstitular(false);
		entity.setIsactivo(true);
		entity.setIscarga(true);
		entity.setIsffaa(false);
		
		Cartera entity2 = new Cartera();
		entity2.setNombre("Ignacio Contreras");
		entity2.setRut(6345123);
		entity2.setRuttitular(6345123);
		entity2.setIstitular(true);
		entity2.setIsactivo(false);
		entity2.setIscarga(true);
		entity.setIsffaa(true);
		
		List<Cartera> carteraCollection = new ArrayList<Cartera>();
		carteraCollection.add(entity);
		carteraCollection.add(entity2);
		service.saveCartera(carteraCollection); //testing Tx
		
		//SVC find All
		List<Cartera>result = service.findAllCartera();
		for (Cartera item : result) {
			System.out.println("ID: " + item.getId() + ", Rut: " + item.getRut() + ", Nombre; " + item.getNombre());
		}
		
		//SVC find by rut
		Cartera entitySearched = service.findCarteraByRut(6345123);
		entitySearched.setNombre("Nombre updated");
		
		//SVC UPDATE
		service.updateCartera(entitySearched);
		
		//SVC find collection by rut titular
		List<Cartera> beneficiados = service.findCarteraByRutTitular(16367363);
	}
	private static void GetByFilterExampleObject(Session session){
		//create a criteria 
		Criteria criteria = session.createCriteria(Cartera.class);
		//create a result variable
		List<Cartera> carteraCollection = new ArrayList<Cartera>();
		
		//search using a example entity
		Cartera exampleSearchEntity = new Cartera();
		exampleSearchEntity.setIsactivo(true);
		exampleSearchEntity.setIstitular(true);
		//add example search entity in criteria
		// http://docs.jboss.org/hibernate/orm/5.1/javadocs/org/hibernate/criterion/Example.html
		criteria.add(Example.create(exampleSearchEntity)
				.ignoreCase()
				.enableLike()
				.excludeZeroes());
		
		//finally resolve expression into Relation DB World
		carteraCollection = criteria.list();
		
	}
	
	private static void GetByFilter(Session session){
		//create a criteria 
		Criteria criteria = session.createCriteria(Cartera.class);
		//create a result variable
		List<Cartera> carteraCollection = new ArrayList<Cartera>();
		//define criteria expression (dynamically)
		criteria.add(Restrictions.like("nombre", "Lopez", MatchMode.ANYWHERE));
		criteria.add(Restrictions.eq("istitular", true));
		criteria.addOrder(Order.asc("nombre") );
		//finally resolve expression into Relation DB World
		carteraCollection = criteria.list();
		
	}

	private static void GetById(Session session){
		int id = 1;
		Cartera entity =  (Cartera)session.get(Cartera.class,id);
	}
	
	private static void GetWithChildRelation(Session session){
		
		int idPrestacion = 1;
		/*FIRST QUERY TO DB OBTAINING PRESTACION*/
		Prestacion entity = (Prestacion)session.get(Prestacion.class,idPrestacion); 
		//now i will get all condiciones from Prestacion through their relationship
		
		//get condiciones especificas from prestacion
		Set<Condicionespecifica> condEspecificaCollection = new HashSet<Condicionespecifica>();
		/*SECOND QUERY TO DB OBTAINING RELATION WITH CONDICIONES ESPECIFICAS (EAGER LOADING)*/
		for (int i = 0; i < entity.getPrestacionCondespecificas().size();i++){
			Condicionespecifica condicion = ((PrestacionCondespecifica)entity.getPrestacionCondespecificas().toArray()[i]).getCondicionespecifica();
			condEspecificaCollection.add(condicion);
			
			System.out.println(String.format("Condicion Especifica: %d : %s", condicion.getId(), condicion.getNombre()));
		}

		//get condiciones generales from prestacion
		/*SECOND QUERY TO DB OBTAINING RELATION WITH CONDICIONES GENERALES (EAGER LOADING)*/
		Set<Condiciongeneral> condGeneralCollection = new HashSet<Condiciongeneral>();
		for (int i = 0; i < entity.getPrestacionCondgenerals().size();i++){
			Condiciongeneral condicion = ((PrestacionCondgeneral)entity.getPrestacionCondgenerals().toArray()[i]).getCondiciongeneral();
			condGeneralCollection.add(condicion);
			
			System.out.println(String.format("Condicion General: %d : %s", condicion.getId(), condicion.getNombre()));
		}
		
		
		
				
				
				
				
	}
}