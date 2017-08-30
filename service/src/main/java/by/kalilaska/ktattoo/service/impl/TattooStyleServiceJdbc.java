package by.kalilaska.ktattoo.service.impl;

import java.util.LinkedList;
import java.util.List;

import by.kalilaska.ktattoo.bean.TattooMasterBean;
import by.kalilaska.ktattoo.bean.TattooStyleBean;
import by.kalilaska.ktattoo.dao.TransactionManager;
import by.kalilaska.ktattoo.dao.impl.TattooStyleDAO;
import by.kalilaska.ktattoo.dataexception.DaoSQLException;
import by.kalilaska.ktattoo.entity.TattooStyleEntity;
import by.kalilaska.ktattoo.service.TattooStyleService;

public class TattooStyleServiceJdbc implements TattooStyleService{
	private TattooStyleDAO tattooStyleDAO;
	private TransactionManager transactionManager;

	public TattooStyleServiceJdbc() {
		tattooStyleDAO = new TattooStyleDAO();
		transactionManager = new TransactionManager();
	}

	@Override
	public List<String> findAllTattooStyleNameByMasterId(int id) {
		LinkedList<String> styleNames = null;
		try {
			List<TattooStyleEntity> styleEntityList = tattooStyleDAO.findAllTattooStyleByMasterId(id);
			if(styleEntityList != null && !styleEntityList.isEmpty()) {
				styleNames = new LinkedList<>();
				for (TattooStyleEntity tattooStyleEntity : styleEntityList) {
					styleNames.add(tattooStyleEntity.getName());
				}
			}
		} catch (DaoSQLException e) {
			//LOG
		}
		return styleNames;
	}

	@Override
	public TattooStyleDAO getDao() {		
		return tattooStyleDAO;
	}

	@Override
	public List<TattooStyleBean> findAllTattooStyleByMasterId(int id) {
		transactionManager.beginTransaction(tattooStyleDAO);
		LinkedList<TattooStyleBean> styleBeanList = null;
		TattooStyleBean tattooStyleBean = null;
		
		try {
			List<TattooStyleEntity> styleEntityList = tattooStyleDAO.findAllTattooStyleByMasterId(id);
			if(styleEntityList != null && !styleEntityList.isEmpty()) {
				styleBeanList = new LinkedList<>();
				
				for (TattooStyleEntity tattooStyleEntity : styleEntityList) {
					tattooStyleBean = new TattooStyleBean(
							tattooStyleEntity.getId(), 
							tattooStyleEntity.getName(), 
							tattooStyleEntity.getDescription());
					styleBeanList.add(tattooStyleBean);
				}
			}
			transactionManager.commit();
		} catch (DaoSQLException e) {
			//LOG
			transactionManager.rollback();
		}
		transactionManager.endTransaction();
		return styleBeanList;
	}

	@Override
	public List<TattooStyleBean> findAll() {
		transactionManager.beginTransaction(tattooStyleDAO);
		List<TattooStyleBean> styleBeanList = null;
		TattooStyleBean tattooStyleBean = null;
		
		try {
			List<TattooStyleEntity> styleEntityList = tattooStyleDAO.findAll();
			if(styleEntityList != null && !styleEntityList.isEmpty()) {
				styleBeanList = new LinkedList<>();
				
				for (TattooStyleEntity tattooStyleEntity : styleEntityList) {
					tattooStyleBean = new TattooStyleBean(
							tattooStyleEntity.getId(), 
							tattooStyleEntity.getName(), 
							tattooStyleEntity.getDescription());
					styleBeanList.add(tattooStyleBean);
				}
			}
			transactionManager.commit();
		}catch(DaoSQLException e){
			transactionManager.rollback();
			//LOG
		}
		transactionManager.endTransaction();
		return styleBeanList;
	}

	@Override
	public List<TattooStyleBean> updateAllTattooStyleByMasterId(TattooMasterBean masterBean) {
		List<TattooStyleBean> styleBeanList = null;
		TattooStyleBean styleBean = null;
		
		int masterId = masterBean.getId();
		List<String> styleIdList = masterBean.getStyleNames();
		
		transactionManager.beginTransaction(tattooStyleDAO);
		try {
			boolean masterStyleCleaned = tattooStyleDAO.cleanAllMasterTattooStyle(masterId);
			
			if(masterStyleCleaned) {
				boolean masterStyleInserted = tattooStyleDAO.insertMasterTattooStyle(masterId, styleIdList);
				
				if(masterStyleInserted) {
					List<TattooStyleEntity> styleEntityList = tattooStyleDAO.findAllTattooStyleByMasterId(masterId);
					
					if(styleEntityList != null) {
						styleBeanList = new LinkedList<>();
						for (TattooStyleEntity tattooStyleEntity : styleEntityList) {
							styleBean = new TattooStyleBean(
									tattooStyleEntity.getId(), 
									tattooStyleEntity.getName(), 
									tattooStyleEntity.getDescription());
							styleBeanList.add(styleBean);
						}
					}
				}
			} 
			transactionManager.commit();
		} catch (DaoSQLException e) {
			transactionManager.rollback();
			//LOG
		}
		transactionManager.endTransaction();
		return styleBeanList;
	}
}
