package by.kalilaska.ktattoo.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import by.kalilaska.ktattoo.dao.AbstractDAO;
import by.kalilaska.ktattoo.dataexception.SQLDataException;
import by.kalilaska.ktattoo.dataname.daoNameList;
import by.kalilaska.ktattoo.entity.TattooPhotoEntity;

public class TattooPhotoDAO extends AbstractDAO<Integer, TattooPhotoEntity> {
	
	private final static String SQL_SELECT_TATTOO_PHOTO_BY_PHOTO_URL = 
			 "SELECT `twph`.`id`, `twph`.`url`, `twph`.`is_done`, `twph`.`FK_master_id` " +					 
					 "FROM `tattoo_work_photo` AS `twph` " +
//					 "INNER JOIN `tattoo_master_info` AS `tmi` ON `twph`.`FK_master_id` = `tmi`.`id` " +
					 "WHERE `twph`.`url` = ?;";
	
	private final static String SQL_SELECT_ALL_TATTOO_PHOTO_BY_MASTER_ID =
			 "SELECT `twph`.`id`, `twph`.`url`, `twph`.`is_done`, `twph`.`FK_master_id` " +					 
					 "FROM `tattoo_work_photo` AS `twph` " +
//					 "INNER JOIN `tattoo_master_info` AS `tmi` ON `twph`.`FK_master_id` = `tmi`.`id` " +
					 "WHERE `twph`.`FK_master_id` = ?;";
	
	private final static String SQL_INSERT_NEW_TATTOO_PHOTO = 
			"INSERT INTO `tattoo_work_photo` (`url`, `FK_master_id`, `is_done`) VALUES (?, ?, ?);";
	
	private final static String SQL_CHANGE_TATTOO_PHOTO_DONE = 
			"UPDATE `tattoo_work_photo` AS `twph` SET `twph`.`is_done` = ? WHERE `twph`.`id` = ?;";
	
	public TattooPhotoEntity findTattooPhotoByPhotoUrl(String photoUrl) throws SQLDataException {
		TattooPhotoEntity photoEntity = null;
		if(photoUrl != null && !photoUrl.isEmpty()) {
			try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_TATTOO_PHOTO_BY_PHOTO_URL)){
				statement.setString(1, photoUrl);
				
				ResultSet resultSet = statement.executeQuery();
				if(resultSet.next()) {
					photoEntity = mapRow(resultSet);
				}				
			} catch (SQLException e) {
				throw new SQLDataException(e);
			}
		}
		return photoEntity;
	}
	
	public List<TattooPhotoEntity> findAllTattooPhotoByMasterId(int id) throws SQLDataException{
		LinkedList<TattooPhotoEntity> tattooPhotos = null;
		TattooPhotoEntity tattooPhoto = null;
		
		try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL_TATTOO_PHOTO_BY_MASTER_ID)){
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			
			if(resultSet.next()) {
				tattooPhotos = new LinkedList<>();
				do {
					tattooPhoto = mapRow(resultSet);
					tattooPhotos.add(tattooPhoto);
				}while(resultSet.next());
			}
		} catch (SQLException e) {
			throw new SQLDataException(e);
		}
		
		return tattooPhotos;
	}


	@Override
	public boolean delete(Integer id) throws SQLDataException {
		throw new UnsupportedOperationException();
	}

	@Override
	public TattooPhotoEntity create(TattooPhotoEntity entity) throws SQLDataException {
		TattooPhotoEntity photoEntity = null;
		String url = entity.getPhotoUrl();
		int masterId = entity.getMasterId();
		boolean isDone = entity.isDone();
		
		try(PreparedStatement statement = connection.prepareStatement(SQL_INSERT_NEW_TATTOO_PHOTO)) {
			statement.setString(1, url);
			statement.setInt(2, masterId);
			statement.setBoolean(3, isDone);
			
			int updateCount = statement.executeUpdate();
			if(updateCount > 0) {
				photoEntity = findTattooPhotoByPhotoUrl(url);
			}
		} catch (SQLException e) {
			throw new SQLDataException(e);
		}
		
		return photoEntity;
	}

	@Override
	public boolean update(TattooPhotoEntity entity) throws SQLDataException {
		throw new UnsupportedOperationException();
	}
	
	public boolean changeTattooPhotoDone(int id, boolean doneValue) throws SQLDataException {
		boolean changed = false;
		try (PreparedStatement statement = connection.prepareStatement(SQL_CHANGE_TATTOO_PHOTO_DONE)){
			statement.setBoolean(1, doneValue);
			statement.setInt(2, id);
			int updateCount = statement.executeUpdate();
			
			if(updateCount > 0) {
				changed = true;
			}
		} catch (SQLException e) {
			throw new SQLDataException(e);
		}
		
		return changed;
	}

	@Override
	protected TattooPhotoEntity mapRow(ResultSet resultSet) throws SQLDataException {
		TattooPhotoEntity tattooPhoto = null;		
		try {
			tattooPhoto = new TattooPhotoEntity();
			tattooPhoto.setId(resultSet.getInt(daoNameList.PHOTO_DAO_RESULTSET_GET_ID));
			tattooPhoto.setPhotoUrl(resultSet.getString(daoNameList.PHOTO_DAO_RESULTSET_GET_URL));
			tattooPhoto.setDone(resultSet.getBoolean(daoNameList.PHOTO_DAO_RESULTSET_GET_IS_DONE));
			tattooPhoto.setMasterId(resultSet.getInt(daoNameList.PHOTO_DAO_RESULTSET_GET_MASTER_ID));
		} catch (SQLException e) {
			throw new SQLDataException(e);
		}
		
		return tattooPhoto;
	}
}
