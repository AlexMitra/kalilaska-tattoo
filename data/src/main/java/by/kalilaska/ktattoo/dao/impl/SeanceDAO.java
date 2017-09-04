package by.kalilaska.ktattoo.dao.impl;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import by.kalilaska.ktattoo.dao.AbstractDAO;
import by.kalilaska.ktattoo.dataexception.SQLDataException;
import by.kalilaska.ktattoo.dataname.daoNameList;
import by.kalilaska.ktattoo.entity.SeanceEntity;

public class SeanceDAO extends AbstractDAO<Integer, SeanceEntity> {
	
	private final static String SQL_SELECT_ALL_APPROVED_SEANCES_BY_CLIENT_ID = 
			"SELECT `s`.`id`, `s`.`date_start`, `s`.`duration_hours`, `s`.`cost_per_hour`, `s`.`FK_account_id`, " +
			"(SELECT `account`.`name` FROM `account` WHERE `account`.`id` = `s`.`FK_account_id`) AS `client`, " +
			"`s`.`FK_master_id`, (SELECT `account`.`name` FROM `account` WHERE `account`.`id` = `s`.`FK_master_id`) AS `master` " +
			"FROM `seance` AS `s` " + 
			"WHERE `s`.`date_end` > now() AND NOT `s`.`cost_per_hour` IS NULL AND `s`.`FK_account_id` = ? ORDER BY `s`.`date_start`;";
	
	private final static String SQL_SELECT_ALL_SEANCES_BY_MASTER_ID = 
			"SELECT `s`.`id`, `s`.`date_start`, `s`.`duration_hours`, `s`.`cost_per_hour`, `s`.`FK_account_id`, " +
			"(SELECT `account`.`name` FROM `account` WHERE `account`.`id` = `s`.`FK_account_id`) AS `client`, " +
			"`s`.`FK_master_id`, (SELECT `account`.`name` FROM `account` WHERE `account`.`id` = `s`.`FK_master_id`) AS `master` " +
			"FROM `seance` AS `s` " + 
			"WHERE `s`.`date_end` > now() AND `s`.`FK_master_id` = ? ORDER BY `s`.`date_start`;";
	
	private final static String SQL_SELECT_ALL_UNAPPROVED_SEANCES_BY_MASTER_ID = 
			"SELECT `s`.`id`, `s`.`date_start`, `s`.`duration_hours`, `s`.`cost_per_hour`, `s`.`FK_account_id`, " +
			"(SELECT `account`.`name` FROM `account` WHERE `account`.`id` = `s`.`FK_account_id`) AS `client`, " +
			"`s`.`FK_master_id`, (SELECT `account`.`name` FROM `account` WHERE `account`.`id` = `s`.`FK_master_id`) AS `master` " +
			"FROM `seance` AS `s` " + 
			"WHERE `s`.`date_end` > now() AND `s`.`cost_per_hour` IS NULL AND `s`.`FK_master_id` = ? ORDER BY `s`.`date_start`;";
	
	private final static String SQL_SELECT_SEANCE_BY_MASTER_ID_DATE = 
			"SELECT `s`.`id`, `s`.`date_start`, `s`.`duration_hours`, `s`.`cost_per_hour`, `s`.`FK_account_id`, " +
			"(SELECT `account`.`name` FROM `account` WHERE `account`.`id` = `s`.`FK_account_id`) AS `client`, " +
			"`s`.`FK_master_id`, (SELECT `account`.`name` FROM `account` WHERE `account`.`id` = `s`.`FK_master_id`) AS `master` " +
			"FROM `seance` AS `s` " + 
			"WHERE `s`.`FK_master_id` = ? AND `s`.`date_start` <= ? AND `s`.`date_end` > ?;";
	
	private final static String SQL_SELECT_SEANCE_BY_CLIENT_ID_DATE = 
			"SELECT `s`.`id`, `s`.`date_start`, `s`.`duration_hours`, `s`.`cost_per_hour`, `s`.`FK_account_id`, " +
			"(SELECT `account`.`name` FROM `account` WHERE `account`.`id` = `s`.`FK_account_id`) AS `client`, " +
			"`s`.`FK_master_id`, (SELECT `account`.`name` FROM `account` WHERE `account`.`id` = `s`.`FK_master_id`) AS `master` " +
			"FROM `seance` AS `s` " + 
			"WHERE `s`.`FK_account_id` = ? AND `s`.`date_start` <= ? AND `s`.`date_end` > ?;";
	
	private final static String SQL_SELECT_SEANCE_BY_CLIENT_ID_MASTER_ID_DATE = 
			"SELECT `s`.`id`, `s`.`date_start`, `s`.`duration_hours`, `s`.`cost_per_hour`, `s`.`FK_account_id`, " +
			"(SELECT `account`.`name` FROM `account` WHERE `account`.`id` = `s`.`FK_account_id`) AS `client`, " +
			"`s`.`FK_master_id`, (SELECT `account`.`name` FROM `account` WHERE `account`.`id` = `s`.`FK_master_id`) AS `master` " +
			"FROM `seance` AS `s` " + 
			"WHERE `s`.`FK_account_id` = ? AND `s`.`FK_master_id` = ? AND `s`.`date_start` <= ? AND `s`.`date_end` > ?;";
	
	private final static String SQL_APPROVE_SEANCE_BY_ID = 
			"UPDATE `seance` AS `s` SET `s`.`cost_per_hour` = ? WHERE `s`.`id` = ?;";
	
	private final static String SQL_INSERT_NEW_SEANCE = 
			"INSERT INTO `seance` (`date_start`, `date_end`, `duration_hours`, `FK_account_id`, `FK_master_id`) VALUES (?, ?, ?, ?, ?);";

	
	public List<SeanceEntity> findAllApprovedSeancesByClientId(int id) throws SQLDataException{
		LinkedList<SeanceEntity> seances = null;
		SeanceEntity seance = null;
		try(PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL_APPROVED_SEANCES_BY_CLIENT_ID)){
			statement.setInt(1, id);			
			ResultSet resultSet = statement.executeQuery();
			
			if (resultSet.next()) {			  
				seances = new LinkedList<> ();
				do {
					seance = mapRow(resultSet);
					seances.add(seance);
				} while (resultSet.next());
			}
			
		}catch (SQLException e) {
			throw new SQLDataException(e);
		}
		
		return seances;
	}
	
	public List<SeanceEntity> findAllSeancesByMasterId(int id) throws SQLDataException{
		LinkedList<SeanceEntity> seances = null;
		SeanceEntity seance = null;
		try(PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL_SEANCES_BY_MASTER_ID)){
			statement.setInt(1, id);			
			ResultSet resultSet = statement.executeQuery();
			
			if (resultSet.next()) {			  
				seances = new LinkedList<> ();
				do {
					seance = mapRow(resultSet);
					seances.add(seance);
				} while (resultSet.next());
			}
			
		}catch (SQLException e) {
			throw new SQLDataException(e);
		}
		
		return seances;
	}
	
	public List<SeanceEntity> findAllUnapprovedSeancesByMasterId(int id) throws SQLDataException{
		LinkedList<SeanceEntity> seances = null;
		SeanceEntity seance = null;
		try(PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL_UNAPPROVED_SEANCES_BY_MASTER_ID)){
			statement.setInt(1, id);			
			ResultSet resultSet = statement.executeQuery();
			
			if (resultSet.next()) {			  
				seances = new LinkedList<> ();
				do {
					seance = mapRow(resultSet);
					seances.add(seance);
				} while (resultSet.next());
			}			
			
		}catch (SQLException e) {
			throw new SQLDataException(e);
		}
		
		return seances;
	}
	
	public SeanceEntity findSeanceByMasterIdAndDate(int id, Date date) throws SQLDataException{		
		SeanceEntity seance = null;
		
		try(PreparedStatement statement = connection.prepareStatement(SQL_SELECT_SEANCE_BY_MASTER_ID_DATE)){
			statement.setInt(1, id);
			statement.setTimestamp(2, new java.sql.Timestamp(date.getTime()));
			statement.setTimestamp(3, new java.sql.Timestamp(date.getTime()));
			ResultSet resultSet = statement.executeQuery();
			
			if (resultSet.next()) {				
				seance = mapRow(resultSet);
			}
			
		}catch (SQLException e) {
			throw new SQLDataException(e);
		}		
		return seance;
	}
	
	public SeanceEntity findSeanceByClientIdAndDate(int id, Date date) throws SQLDataException{		
		SeanceEntity seance = null;
		
		try(PreparedStatement statement = connection.prepareStatement(SQL_SELECT_SEANCE_BY_CLIENT_ID_DATE)){
			statement.setInt(1, id);
			statement.setTimestamp(2, new java.sql.Timestamp(date.getTime()));
			statement.setTimestamp(3, new java.sql.Timestamp(date.getTime()));
			
			ResultSet resultSet = statement.executeQuery();			
			if (resultSet.next()) {				
				seance = mapRow(resultSet);
			}
			
		}catch (SQLException e) {
			throw new SQLDataException(e);
		}		
		return seance;
	}
	
	public SeanceEntity findSeanceByClientIdAndMasterIdAndDate(int clientId, int masterId, Date date) throws SQLDataException{		
		SeanceEntity seance = null;
		
		try(PreparedStatement statement = connection.prepareStatement(SQL_SELECT_SEANCE_BY_CLIENT_ID_MASTER_ID_DATE)){
			statement.setInt(1, clientId);
			statement.setInt(2, masterId);
			statement.setTimestamp(3, new java.sql.Timestamp(date.getTime()));
			statement.setTimestamp(4, new java.sql.Timestamp(date.getTime()));
			ResultSet resultSet = statement.executeQuery();
			
			if (resultSet.next()) {				
				seance = mapRow(resultSet);
			}
			
		}catch (SQLException e) {
			throw new SQLDataException(e);
		}		
		return seance;
	}
	
	public boolean approveSeanceById(int id, BigDecimal cost) throws SQLDataException{		
		boolean approved = false;
		
		try(PreparedStatement statement = connection.prepareStatement(SQL_APPROVE_SEANCE_BY_ID)){			
			statement.setBigDecimal(1, cost);
			statement.setInt(2, id);
			
			int updateCount = statement.executeUpdate();			
			if (updateCount > 0) {				
				approved = true;
			}
			
		}catch (SQLException e) {
			throw new SQLDataException(e);
		}		
		return approved;
	}
	
	

	@Override
	public boolean delete(Integer id) {
		throw new UnsupportedOperationException();
	}

	@Override
	public SeanceEntity create(SeanceEntity entity) throws SQLDataException {
		SeanceEntity created = null;
		if(entity != null) {
			int clientId = entity.getClientId();
			int masterId = entity.getMasterId();
			Date dateStart = entity.getDateStart();
			Date dateEnd = entity.getDateEnd();
			byte duration = entity.getDuration();
			
			try(PreparedStatement statement = connection.prepareStatement(SQL_INSERT_NEW_SEANCE)){
				statement.setTimestamp(1, new java.sql.Timestamp(dateStart.getTime()));
				statement.setTimestamp(2, new java.sql.Timestamp(dateEnd.getTime()));
				statement.setByte(3, duration);
				statement.setInt(4, clientId);
				statement.setInt(5, masterId);
				
				int executeUpdate = statement.executeUpdate();
				
				if(executeUpdate > 0) {
					created = findSeanceByClientIdAndMasterIdAndDate(clientId, masterId, dateStart);
				}
			} catch (SQLException e) {
				throw new SQLDataException(e);
			}
		}
		
		return created;
	}

	@Override
	public boolean update(SeanceEntity entity) {
		throw new UnsupportedOperationException();
	}

	@Override
	protected SeanceEntity mapRow(ResultSet resultSet) throws SQLDataException {
		SeanceEntity seance = null;
		try {
			Date date = null;
			Timestamp timestamp = resultSet.getTimestamp(daoNameList.SEANCE_DAO_RESULTSET_GET_DATE_START);
			if (timestamp != null)
			    date = new Date(timestamp.getTime());
			
			seance = new SeanceEntity(resultSet.getInt(daoNameList.SEANCE_DAO_RESULTSET_GET_ID), 
					date, 
					resultSet.getByte(daoNameList.SEANCE_DAO_RESULTSET_GET_DURATION), 
					resultSet.getBigDecimal(daoNameList.SEANCE_DAO_RESULTSET_GET_COST), 
					resultSet.getInt(daoNameList.SEANCE_DAO_RESULTSET_GET_CLIENT_ID), 
					resultSet.getString(daoNameList.SEANCE_DAO_RESULTSET_GET_CLIENT_NAME), 
					resultSet.getInt(daoNameList.SEANCE_DAO_RESULTSET_GET_MASTER_ID), 
					resultSet.getString(daoNameList.SEANCE_DAO_RESULTSET_GET_MASTER_NAME));
		} catch (SQLException e) {
			throw new SQLDataException(e);
		}
		return seance;
	}

}
