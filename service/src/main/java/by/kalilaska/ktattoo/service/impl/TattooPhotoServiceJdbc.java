package by.kalilaska.ktattoo.service.impl;

import java.util.LinkedList;
import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.kalilaska.ktattoo.bean.TattooPhotoBean;
import by.kalilaska.ktattoo.dao.TransactionManager;
import by.kalilaska.ktattoo.dao.impl.TattooPhotoDAO;
import by.kalilaska.ktattoo.dataexception.SQLDataException;
import by.kalilaska.ktattoo.entity.TattooPhotoEntity;
import by.kalilaska.ktattoo.service.TattooPhotoService;
import by.kalilaska.ktattoo.service.DaoFactory;

public class TattooPhotoServiceJdbc implements TattooPhotoService {
	private final static Logger LOGGER = LogManager.getLogger(TattooPhotoServiceJdbc.class);
	private TattooPhotoDAO tattooPhotoDAO;
	private TransactionManager transactionManager;

	public TattooPhotoServiceJdbc() {		
		tattooPhotoDAO = DaoFactory.createDao(this.getClass());
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

					tattooPhotoBean = convertEntityToBean(tattooPhotoEntity);
					tattooPhotoBeanList.add(tattooPhotoBean);
				}
			}
		} catch (SQLDataException e) {
			LOGGER.log(Level.ERROR, "Exception in TattooPhotoDAO: " + e.getMessage());
		}
		
		return tattooPhotoBeanList;
	}

	@Override
	public TattooPhotoDAO getDao() {		
		return tattooPhotoDAO;
	}

}
