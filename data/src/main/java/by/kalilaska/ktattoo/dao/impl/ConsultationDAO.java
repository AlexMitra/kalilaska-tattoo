package by.kalilaska.ktattoo.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import by.kalilaska.ktattoo.dao.AbstractDAO;
import by.kalilaska.ktattoo.dataexception.SQLDataException;
import by.kalilaska.ktattoo.dataname.daoNameList;
import by.kalilaska.ktattoo.entity.ConsultationEntity;

public class ConsultationDAO extends AbstractDAO<Integer, ConsultationEntity> {
	
	private final static String SQL_SELECT_ALL_CONSULTATIONS_BY_CLIENT_ID = 
			"SELECT `c`.`id`, `c`.`date_start`, `c`.`FK_account_id`, " +
			"(SELECT `account`.`name` FROM `account` WHERE `account`.`id` = `c`.`FK_account_id`) AS `client`, " +
			"`c`.`FK_master_id`, (SELECT `account`.`name` FROM `account` WHERE `account`.`id` = `c`.`FK_master_id`) AS `master`, `c`.`is_approved`" +
			"FROM `consultation` AS `c` " + 
			"INNER JOIN `account` ON `c`.`FK_account_id` = `account`.`id` " + 
			"WHERE `c`.`FK_account_id` = ? ORDER BY `c`.`date_start`;";
	
	private final static String SQL_SELECT_ALL_CONSULTATIONS_BY_MASTER_ID = 
			"SELECT `c`.`id`, `c`.`date_start`, `c`.`FK_account_id`, " +
			"(SELECT `account`.`name` FROM `account` WHERE `account`.`id` = `c`.`FK_account_id`) AS `client`, " +
			"`c`.`FK_master_id`, (SELECT `account`.`name` FROM `account` WHERE `account`.`id` = `c`.`FK_master_id`) AS `master`, `c`.`is_approved`" +
			"FROM `consultation` AS `c` " + 
			"INNER JOIN `account` ON `c`.`FK_master_id` = `account`.`id` " + 
			"WHERE `c`.`FK_master_id` = ? ORDER BY `c`.`date_start`;";
	
	private final static String SQL_SELECT_CONSULTATIONS_BY_MASTER_ID_DATE = 
			"SELECT `c`.`id`, `c`.`date_start`, `c`.`FK_account_id`, " +
			"(SELECT `account`.`name` FROM `account` WHERE `account`.`id` = `c`.`FK_account_id`) AS `client`, " +
			"`c`.`FK_master_id`, (SELECT `account`.`name` FROM `account` WHERE `account`.`id` = `c`.`FK_master_id`) AS `master`, `c`.`is_approved`" +
			"FROM `consultation` AS `c` " + 
			"INNER JOIN `account` ON `c`.`FK_master_id` = `account`.`id` " + 
			"WHERE `c`.`FK_master_id` = ? AND `c`.`date_start` <= ? AND `c`.`date_end` > ?;";
	
	private final static String SQL_SELECT_CONSULTATIONS_BY_CLIENT_ID_DATE = 
			"SELECT `c`.`id`, `c`.`date_start`, `c`.`FK_account_id`, " +
			"(SELECT `account`.`name` FROM `account` WHERE `account`.`id` = `c`.`FK_account_id`) AS `client`, " +
			"`c`.`FK_master_id`, (SELECT `account`.`name` FROM `account` WHERE `account`.`id` = `c`.`FK_master_id`) AS `master`, `c`.`is_approved`" +
			"FROM `consultation` AS `c` " + 
			"INNER JOIN `account` ON `c`.`FK_master_id` = `account`.`id` " + 
			"WHERE `c`.`FK_account_id` = ? AND `c`.`date_start` <= ? AND `c`.`date_end` > ?;";
	
	private final static String SQL_SELECT_CONSULTATIONS_BY_CLIENT_ID_MASTER_ID_DATE = 
			"SELECT `c`.`id`, `c`.`date_start`, `c`.`FK_account_id`, " +
			"(SELECT `account`.`name` FROM `account` WHERE `account`.`id` = `c`.`FK_account_id`) AS `client`, " +
			"`c`.`FK_master_id`, (SELECT `account`.`name` FROM `account` WHERE `account`.`id` = `c`.`FK_master_id`) AS `master`, `c`.`is_approved`" +
			"FROM `consultation` AS `c` " + 
			"INNER JOIN `account` ON `c`.`FK_master_id` = `account`.`id` " + 
			"WHERE `c`.`FK_account_id` = ? AND `c`.`FK_master_id` = ? AND `c`.`date_start` <= ? AND `c`.`date_end` > ?;";
	
	private final static String SQL_INSERT_NEW_CONSULTATION = 
			"INSERT INTO `consultation` (`date_start`, `date_end`, `FK_account_id`, `FK_master_id`) VALUES (?, ?, ?, ?);";

	public List<ConsultationEntity> findAllConsultationsByClientId(int id) throws SQLDataException{
		LinkedList<ConsultationEntity> consultations = null;
		ConsultationEntity consultation = null;
		try(PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL_CONSULTATIONS_BY_CLIENT_ID)){
			statement.setInt(1, id);			
			ResultSet resultSet = statement.executeQuery();
			
			if (resultSet.next()) {			  
				consultations = new LinkedList<ConsultationEntity> ();
				do {
					consultation = mapRow(resultSet);					
					consultations.add(consultation);
				} while (resultSet.next());
			}
			
		}catch (SQLException e) {
			throw new SQLDataException(e);
		}
		
		return consultations;
	}
	
	public List<ConsultationEntity> findAllConsultationsByMasterId(int id) throws SQLDataException{
		LinkedList<ConsultationEntity> consultations = null;
		ConsultationEntity consultation = null;
		
		try(PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL_CONSULTATIONS_BY_MASTER_ID)){
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			
			if(resultSet.next()) {
				consultations = new LinkedList<>();
				do {
					consultation = mapRow(resultSet);					
					consultations.add(consultation);
				}while(resultSet.next());
			}
		}catch (SQLException e) {
			throw new SQLDataException(e);
		}
		
		return consultations;
	}
	
	public ConsultationEntity findConsultationByMasterIdAndDate (int masterId, Date date) throws SQLDataException {
		ConsultationEntity consultation = null;
		try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_CONSULTATIONS_BY_MASTER_ID_DATE)){			
			statement.setInt(1, masterId);
			statement.setTimestamp(2, new java.sql.Timestamp(date.getTime()));
			statement.setTimestamp(3, new java.sql.Timestamp(date.getTime()));
			
			ResultSet resultSet = statement.executeQuery();
			if(resultSet.next()) {
				consultation = mapRow(resultSet);
			}
		} catch (SQLException e) {
			throw new SQLDataException(e);
		}
		
		return consultation;
	}
	
	public ConsultationEntity findConsultationByClientIdAndDate (int clientId, Date date) throws SQLDataException {
		ConsultationEntity consultation = null;
		try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_CONSULTATIONS_BY_CLIENT_ID_DATE)){			
			statement.setInt(1, clientId);
			statement.setTimestamp(2, new java.sql.Timestamp(date.getTime()));
			statement.setTimestamp(3, new java.sql.Timestamp(date.getTime()));
			
			ResultSet resultSet = statement.executeQuery();
			if(resultSet.next()) {
				consultation = mapRow(resultSet);
			}
		} catch (SQLException e) {
			throw new SQLDataException(e);
		}
		
		return consultation;
	}
	
	public ConsultationEntity findConsultationByClientIdAndMasterIdAndDate (int clientId, int masterId, Date date) throws SQLDataException {
		ConsultationEntity consultation = null;
		try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_CONSULTATIONS_BY_CLIENT_ID_MASTER_ID_DATE)){
			statement.setInt(1, clientId);
			statement.setInt(2, masterId);
			statement.setTimestamp(3, new java.sql.Timestamp(date.getTime()));
			statement.setTimestamp(4, new java.sql.Timestamp(date.getTime()));
			
			ResultSet resultSet = statement.executeQuery();
			if(resultSet.next()) {
				consultation = mapRow(resultSet);
			}
		} catch (SQLException e) {
			throw new SQLDataException(e);
		}
		
		return consultation;
	}


	@Override
	public boolean delete(Integer id) {
		throw new UnsupportedOperationException();
	}

	@Override
	public ConsultationEntity create(ConsultationEntity entity) throws SQLDataException {
		int clientId = entity.getClientId();
		int masterId = entity.getMasterId();
		Date dateStart = entity.getDateStart();
		
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(dateStart);
		calendar.add(Calendar.HOUR_OF_DAY, daoNameList.DEFAULT_CONSULTATION_DURATION);
		Date dateEnd = calendar.getTime();
		
		try (PreparedStatement statement = connection.prepareStatement(SQL_INSERT_NEW_CONSULTATION)){
			statement.setTimestamp(1, new java.sql.Timestamp(dateStart.getTime()));
			statement.setTimestamp(2, new java.sql.Timestamp(dateEnd.getTime()));
			statement.setInt(3, clientId);
			statement.setInt(4, masterId);			
			
			int executeUpdate = statement.executeUpdate();
			
			if(executeUpdate > 0) {
				entity = findConsultationByClientIdAndMasterIdAndDate(clientId, masterId, dateStart);
			}else {
				entity = null;
			}
		} catch (SQLException e) {
			throw new SQLDataException(e);
		}
		
		return entity;
	}

	@Override
	public boolean update(ConsultationEntity entity) {
		throw new UnsupportedOperationException();
	}

	@Override
	protected ConsultationEntity mapRow(ResultSet resultSet) throws SQLDataException {
		ConsultationEntity consultation = null;
		try {
			Date dateStart = null;
			Timestamp timestamp = resultSet.getTimestamp("date_start");
			if (timestamp != null)
			    dateStart = new Date(timestamp.getTime());
			
			consultation = new ConsultationEntity(resultSet.getInt("id"),					
					dateStart, 
					resultSet.getInt("FK_account_id"), 
					resultSet.getString("client"), 
					resultSet.getInt("FK_master_id"), 
					resultSet.getString("master"), 
					resultSet.getBoolean("is_approved"));
		} catch (SQLException e) {
			throw new SQLDataException(e);
		}
		return consultation;
	}
}
