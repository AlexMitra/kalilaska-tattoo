package by.kalilaska.ktattoo.service;

import java.util.HashMap;

import by.kalilaska.ktattoo.dao.AbstractDAO;
import by.kalilaska.ktattoo.dao.impl.AccountDAO;
import by.kalilaska.ktattoo.dao.impl.ConsultationDAO;
import by.kalilaska.ktattoo.dao.impl.RoleDAO;
import by.kalilaska.ktattoo.dao.impl.SeanceDAO;
import by.kalilaska.ktattoo.dao.impl.TattooMasterDAO;
import by.kalilaska.ktattoo.dao.impl.TattooPhotoDAO;
import by.kalilaska.ktattoo.dao.impl.TattooStyleDAO;
import by.kalilaska.ktattoo.service.impl.AccountServiceJdbc;
import by.kalilaska.ktattoo.service.impl.ConsultationServiceJdbc;
import by.kalilaska.ktattoo.service.impl.RoleServiceJdbc;
import by.kalilaska.ktattoo.service.impl.SeanceServiceJdbc;
import by.kalilaska.ktattoo.service.impl.TattooMasterServiceJdbc;
import by.kalilaska.ktattoo.service.impl.TattooPhotoServiceJdbc;
import by.kalilaska.ktattoo.service.impl.TattooStyleServiceJdbc;

public class DaoFactory {	
	private final static HashMap<Class<? extends BaseService>, AbstractDAO> serviceDaoMap= new HashMap<>();

	static {
		serviceDaoMap.put(AccountServiceJdbc.class, new AccountDAO());
		serviceDaoMap.put(RoleServiceJdbc.class, new RoleDAO());
		serviceDaoMap.put(TattooMasterServiceJdbc.class, new TattooMasterDAO());
		serviceDaoMap.put(ConsultationServiceJdbc.class, new ConsultationDAO());
		serviceDaoMap.put(SeanceServiceJdbc.class, new SeanceDAO());
		serviceDaoMap.put(TattooPhotoServiceJdbc.class, new TattooPhotoDAO());
		serviceDaoMap.put(TattooStyleServiceJdbc.class, new TattooStyleDAO());
	}
	
	public static <T extends AbstractDAO> T createDao(Class<? extends BaseService> serviceClazz) {		
		T dao = (T) serviceDaoMap.get(serviceClazz);
		return dao;
	}
}
