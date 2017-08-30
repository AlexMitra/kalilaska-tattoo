package by.kalilaska.ktattoo.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import by.kalilaska.ktattoo.dao.AbstractDAO;
import by.kalilaska.ktattoo.dataexception.DaoSQLException;
import by.kalilaska.ktattoo.entity.TattooMasterEntity;

public class TattooMasterDAO extends AbstractDAO<Integer, TattooMasterEntity>{
	private final static String SQL_SELECT_ALL_ALLOWED_MASTERS =
			 "SELECT `a`.`id`, `a`.`name`, `a`.`photo_url`, `tmi`.`about_info`" +					 
					 "FROM `account` AS `a` " +
					 "INNER JOIN `tattoo_master_info` AS `tmi` ON `a`.`id` = `tmi`.`id` " +
					 "WHERE `a`.`is_allowed` = true;";
	
	private final static String SQL_SELECT_MASTER_BY_ID =
			 "SELECT `a`.`id`, `a`.`name`, `a`.`photo_url`, `tmi`.`about_info`" +					 
					 "FROM `account` AS `a` " +
					 "INNER JOIN `tattoo_master_info` AS `tmi` ON `a`.`id` = `tmi`.`id` " +
					 "WHERE `a`.`id` = ?;";
	
	private final static String SQL_INSERT_MASTER =
			 "INSERT INTO `tattoo_master_info` (`id`, `about_info`) VALUES (?, ?);";
	
	private final static String SQL_UPDATE_MASTER_BY_ID = 
			"UPDATE `tattoo_master_info` AS `tmi` " + 
					"SET `tmi`.`about_info` = ? " + 
					"WHERE `tmi`.`id` = ?;";
	
	private final static String SQL_DELETE_MASTER_BY_ID = 
			"DELETE FROM `tattoo_master_info` WHERE `tattoo_master_info`.`id` = ?;";

	@Override
	public List<TattooMasterEntity> findAll() throws DaoSQLException {
		LinkedList<TattooMasterEntity> masters = null;
		TattooMasterEntity master = null;
		
		try(PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL_ALLOWED_MASTERS)){
			ResultSet resultSet = statement.executeQuery();
			if(resultSet.next()) {
				masters = new LinkedList<>();
				do {
					master = mapRow(resultSet);
					masters.add(master);
				}while(resultSet.next());
			}
		} catch (SQLException e) {
			throw new DaoSQLException(e);
		}
		return masters;
	}

	@Override
	public TattooMasterEntity findById(Integer id) throws DaoSQLException {
		TattooMasterEntity master = null;
		if(id != null) {
			try(PreparedStatement statement = connection.prepareStatement(SQL_SELECT_MASTER_BY_ID)) {
				statement.setInt(1, id);
				ResultSet resultSet = statement.executeQuery();
				if(resultSet.next()) {
					master = mapRow(resultSet);
				}
			} catch (SQLException e) {
				//LOG
				throw new DaoSQLException(e);
			}
		}		
		return master;
	}

	@Override
	public boolean delete(Integer id) throws DaoSQLException {
		boolean deleted = false;
		if(id != null) {
			try(PreparedStatement statement = connection.prepareStatement(SQL_DELETE_MASTER_BY_ID)) {
				statement.setInt(1, id);
				
				int updateCount = statement.executeUpdate();
				if(updateCount > 0) {
					deleted = true;
				}
			} catch (SQLException e) {
				throw new DaoSQLException(e);
			}
		}		
		return deleted;
	}

	@Override
	public boolean delete(TattooMasterEntity entity) {
		throw new UnsupportedOperationException();
	}

	@Override
	public TattooMasterEntity create(TattooMasterEntity entity) throws DaoSQLException {
		TattooMasterEntity masterEntity = null;
		int id = entity.getId();
		String aboutInfo = entity.getAboutInfo();
		
		try(PreparedStatement statement = connection.prepareStatement(SQL_INSERT_MASTER)) {
			statement.setInt(1, id);
			statement.setString(2, aboutInfo);
			
			int executeCount = statement.executeUpdate();			
			if(executeCount > 0) {
				masterEntity = findById(id);
			}
		} catch (SQLException e) {
			// LOG
			throw new DaoSQLException(e);
		}
		
		return masterEntity;
	}

	@Override
	public boolean update(TattooMasterEntity entity) throws DaoSQLException {
		boolean result = false;
		int id = entity.getId();
		String aboutInfo = entity.getAboutInfo();
		
		try(PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_MASTER_BY_ID)) {
			statement.setString(1, aboutInfo);
			statement.setInt(2, id);
			
			int updateCount = statement.executeUpdate();
			if(updateCount > 0) {
				result = true;
			}
		} catch (SQLException e) {			
			throw new DaoSQLException(e);
		}
			
		return result;
	}

	@Override
	protected TattooMasterEntity mapRow(ResultSet resultSet) throws DaoSQLException {
		TattooMasterEntity master = null;
		try {
			master = new TattooMasterEntity();
			master.setId(resultSet.getInt("id"));
			master.setName(resultSet.getString("name"));
			master.setPhotoUrl(resultSet.getString("photo_url"));
			master.setAboutInfo(resultSet.getString("about_info"));
		}catch(SQLException e) {
			throw new DaoSQLException(e);
		}
		return master;
	}

}
