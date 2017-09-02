package by.kalilaska.ktattoo.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import by.kalilaska.ktattoo.dao.AbstractDAO;
import by.kalilaska.ktattoo.dataexception.SQLDataException;
import by.kalilaska.ktattoo.entity.TattooPhotoEntity;

public class TattooPhotoDAO extends AbstractDAO<Integer, TattooPhotoEntity> {
	
	private final static String SQL_SELECT_ALL_TATTOO_PHOTO_BY_MASTER_ID =
			 "SELECT `twph`.`id`, `twph`.`url`, `twph`.`is_done`, `twph`.`FK_master_id` " +					 
					 "FROM `tattoo_work_photo` AS `twph` " +
					 "INNER JOIN `tattoo_master_info` AS `tmi` ON `twph`.`FK_master_id` = `tmi`.`id` " +
					 "WHERE `tmi`.`id` = ?;";
	
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
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean update(TattooPhotoEntity entity) throws SQLDataException {
		throw new UnsupportedOperationException();
	}

	@Override
	protected TattooPhotoEntity mapRow(ResultSet resultSet) throws SQLDataException {
		TattooPhotoEntity tattooPhoto = null;		
		try {
			tattooPhoto = new TattooPhotoEntity();
			tattooPhoto.setId(resultSet.getInt("id"));
			tattooPhoto.setPhotoUrl(resultSet.getString("url"));
			tattooPhoto.setDone(resultSet.getBoolean("is_done"));
			tattooPhoto.setMasterId(resultSet.getInt("FK_master_id"));
		} catch (SQLException e) {
			throw new SQLDataException(e);
		}
		
		return tattooPhoto;
	}
}
