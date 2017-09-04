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
import by.kalilaska.ktattoo.service.impl.AccountServiceImpl;
import by.kalilaska.ktattoo.service.impl.ConsultationServiceImpl;
import by.kalilaska.ktattoo.service.impl.RoleServiceImpl;
import by.kalilaska.ktattoo.service.impl.SeanceServiceImpl;
import by.kalilaska.ktattoo.service.impl.TattooMasterServiceImpl;
import by.kalilaska.ktattoo.service.impl.TattooPhotoServiceImpl;
import by.kalilaska.ktattoo.service.impl.TattooStyleServiceImpl;

public class DaoFactory {	
	private final static HashMap<Class<? extends BaseService>, AbstractDAO> serviceDaoMap= new HashMap<>();

	static {
		serviceDaoMap.put(AccountServiceImpl.class, new AccountDAO());
		serviceDaoMap.put(RoleServiceImpl.class, new RoleDAO());
		serviceDaoMap.put(TattooMasterServiceImpl.class, new TattooMasterDAO());
		serviceDaoMap.put(ConsultationServiceImpl.class, new ConsultationDAO());
		serviceDaoMap.put(SeanceServiceImpl.class, new SeanceDAO());
		serviceDaoMap.put(TattooPhotoServiceImpl.class, new TattooPhotoDAO());
		serviceDaoMap.put(TattooStyleServiceImpl.class, new TattooStyleDAO());
	}
	
	public static <T extends AbstractDAO> T createDao(Class<? extends BaseService> serviceClazz) {		
		T dao = (T) serviceDaoMap.get(serviceClazz);
		return dao;
	}
}
