package by.kalilaska.ktattoo.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import by.kalilaska.ktattoo.dao.AbstractDAO;
import by.kalilaska.ktattoo.dataexception.DaoSQLException;
import by.kalilaska.ktattoo.entity.ConsultationEntity;

public class ConsultationDAO extends AbstractDAO<Integer, ConsultationEntity> {
	
//	private final static String SQL_SELECT_ALL_CONSULTATIONS_BY_CLIENT_ID = 
//			"SELECT `c`.`id`, `c`.`date`, `c`.`FK_account_id`, `c`.`FK_master_id`, FROM `consultation` AS `c` " + 
//			"INNER JOIN `account` ON `c`.`FK_account_id` = `account`.`id` " + 
//			"WHERE `c`.`FK_account_id` = ?;";
	
	private final static String SQL_SELECT_ALL_CONSULTATIONS_BY_CLIENT_ID = 
			"SELECT `c`.`id`, `c`.`date`, `c`.`FK_account_id`, " +
			"(SELECT `account`.`name` FROM `account` WHERE `account`.`id` = `c`.`FK_account_id`) AS `client`, " +
			"`c`.`FK_master_id`, (SELECT `account`.`name` FROM `account` WHERE `account`.`id` = `c`.`FK_master_id`) AS `master` " +
			"FROM `consultation` AS `c` " + 
			"INNER JOIN `account` ON `c`.`FK_account_id` = `account`.`id` " + 
			"WHERE `c`.`FK_account_id` = ?;";
	
//	private final static String SQL_SELECT_ALL_CONSULTATIONS_BY_MASTER_ID = 
//			"SELECT `c`.`id`, `c`.`date`, `c`.`FK_account_id`, `c`.`FK_master_id` FROM `consultation` AS `c` " + 
//			"INNER JOIN `account` ON `c`.`FK_master_id` = `account`.`id` " + 
//			"WHERE `c`.`FK_master_id` = ?;";
	
	private final static String SQL_SELECT_ALL_CONSULTATIONS_BY_MASTER_ID = 
			"SELECT `c`.`id`, `c`.`date`, `c`.`FK_account_id`, " +
			"(SELECT `account`.`name` FROM `account` WHERE `account`.`id` = `c`.`FK_account_id`) AS `client`, " +
			"`c`.`FK_master_id`, (SELECT `account`.`name` FROM `account` WHERE `account`.`id` = `c`.`FK_master_id`) AS `master` " +
			"FROM `consultation` AS `c` " + 
			"INNER JOIN `account` ON `c`.`FK_master_id` = `account`.`id` " + 
			"WHERE `c`.`FK_master_id` = ?;";

	@Override
	public List<ConsultationEntity> findAll() {
		throw new UnsupportedOperationException();
	}	

	public List<ConsultationEntity> findAllConsultationsByClientId(int id) throws DaoSQLException{
		LinkedList<ConsultationEntity> consultations = null;
		ConsultationEntity consultation = null;
		try(PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL_CONSULTATIONS_BY_CLIENT_ID)){
			statement.setInt(1, id);			
			ResultSet resultSet = statement.executeQuery();
			
			if (resultSet.next()) {			  
				consultations = new LinkedList<ConsultationEntity> ();
				do {
//					consultation = new ConsultationEntity(resultSet.getInt("id"), 
//							resultSet.getDate("date"), 
//							resultSet.getInt("FK_account_id"), 
//							resultSet.getString("client"), 
//							resultSet.getInt("FK_master_id"), 
//							resultSet.getString("master"));
					consultation = mapRow(resultSet);					
					consultations.add(consultation);
				} while (resultSet.next());
			}
			
		}catch (SQLException e) {
			throw new DaoSQLException(e);
		}
		
		return consultations;
	}
	
	public List<ConsultationEntity> findAllConsultationsByMasterId(int id) throws DaoSQLException{
		LinkedList<ConsultationEntity> consultations = null;
		ConsultationEntity consultation = null;
		
		try(PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL_CONSULTATIONS_BY_MASTER_ID)){
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			
			if(resultSet.next()) {
				consultations = new LinkedList<>();
				do {
//					consultation = new ConsultationEntity(resultSet.getInt("id"), 
//							resultSet.getDate("date"), 
//							resultSet.getInt("FK_account_id"), 
//							resultSet.getString("client"), 
//							resultSet.getInt("FK_master_id"), 
//							resultSet.getString("master"));
					consultation = mapRow(resultSet);					
					consultations.add(consultation);
				}while(resultSet.next());
			}
		}catch (SQLException e) {
			throw new DaoSQLException(e);
		}
		
		return consultations;
	}

	@Override
	public ConsultationEntity findById(Integer id) {
		throw new UnsupportedOperationException();
	}	

	@Override
	public boolean delete(Integer id) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean delete(ConsultationEntity entity) {
		throw new UnsupportedOperationException();
	}

	@Override
	public ConsultationEntity create(ConsultationEntity entity) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean update(ConsultationEntity entity) {
		throw new UnsupportedOperationException();
	}

	@Override
	protected ConsultationEntity mapRow(ResultSet resultSet) throws DaoSQLException {
		ConsultationEntity consultation = null;
		try {
			Date date = null;
			Timestamp timestamp = resultSet.getTimestamp("date");
			if (timestamp != null)
			    date = new Date(timestamp.getTime());
			
			consultation = new ConsultationEntity(resultSet.getInt("id"),					
					date, 
					resultSet.getInt("FK_account_id"), 
					resultSet.getString("client"), 
					resultSet.getInt("FK_master_id"), 
					resultSet.getString("master"));
		} catch (SQLException e) {
			throw new DaoSQLException(e);
		}
		return consultation;
	}



}
