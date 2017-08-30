package by.kalilaska.ktattoo.service.impl;

import java.util.LinkedList;
import java.util.List;

import by.kalilaska.ktattoo.bean.TattooPhotoBean;
import by.kalilaska.ktattoo.dao.TransactionManager;
import by.kalilaska.ktattoo.dao.impl.TattooPhotoDAO;
import by.kalilaska.ktattoo.dataexception.DaoSQLException;
import by.kalilaska.ktattoo.entity.TattooPhotoEntity;
import by.kalilaska.ktattoo.service.TattooPhotoService;

public class TattooPhotoServiceJdbc implements TattooPhotoService {
	
	private TattooPhotoDAO tattooPhotoDAO;
	private TransactionManager transactionManager;

	public TattooPhotoServiceJdbc() {
		tattooPhotoDAO = new TattooPhotoDAO();
		transactionManager = new TransactionManager();
	}

	@Override
	public List<TattooPhotoBean> findAllTattooPhotoByMasterId(int id) {
		LinkedList<TattooPhotoBean> tattooPhotoBeanList = null;
		TattooPhotoBean tattooPhotoBean = null;
		
		List<TattooPhotoEntity> tattooPhotoEntityList;
		try {
			tattooPhotoEntityList = tattooPhotoDAO.findAllTattooPhotoByMasterId(id);
			if(tattooPhotoEntityList != null && !tattooPhotoEntityList.isEmpty()) {
				tattooPhotoBeanList = new LinkedList<>();
				
				for (TattooPhotoEntity tattooPhotoEntity : tattooPhotoEntityList) {
					tattooPhotoBean = 
							new TattooPhotoBean(tattooPhotoEntity.getId(), 
							tattooPhotoEntity.getPhotoUrl(), 
							tattooPhotoEntity.isDone());
					tattooPhotoBeanList.add(tattooPhotoBean);
				}
			}
		} catch (DaoSQLException e) {
			//LOG
		}
		
		return tattooPhotoBeanList;
	}

	@Override
	public TattooPhotoDAO getDao() {		
		return tattooPhotoDAO;
	}

}
