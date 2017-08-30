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
import by.kalilaska.ktattoo.entity.SeanceEntity;

public class SeanceDAO extends AbstractDAO<Integer, SeanceEntity> {
	
	private final static String SQL_SELECT_ALL_SEANCES_BY_CLIENT_ID = 
			"SELECT `s`.`id`, `s`.`date`, `s`.`duration_hours`, `s`.`cost_per_hour`, `s`.`FK_account_id`, " +
			"(SELECT `account`.`name` FROM `account` WHERE `account`.`id` = `s`.`FK_account_id`) AS `client`, " +
			"`s`.`FK_master_id`, (SELECT `account`.`name` FROM `account` WHERE `account`.`id` = `s`.`FK_master_id`) AS `master` " +
			"FROM `seance` AS `s` " + 
			"INNER JOIN `account` ON `s`.`FK_account_id` = `account`.`id` " + 
			"WHERE `s`.`FK_account_id` = ?;";


	@Override
	public List<SeanceEntity> findAll() {
		throw new UnsupportedOperationException();
	}
	
	public List<SeanceEntity> findAllSeancesByClientId(int id) throws DaoSQLException{
		LinkedList<SeanceEntity> seances = null;
		SeanceEntity seance = null;
		try(PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL_SEANCES_BY_CLIENT_ID)){
			statement.setInt(1, id);			
			ResultSet resultSet = statement.executeQuery();
			
			if (resultSet.next()) {			  
				seances = new LinkedList<SeanceEntity> ();
				do {
//					seance = new SeanceEntity(resultSet.getInt("id"), 
//							resultSet.getDate("date"), 
//							resultSet.getByte("duration_hours"), 
//							resultSet.getBigDecimal("cost_per_hour"), 
//							resultSet.getInt("FK_account_id"), 
//							resultSet.getString("client"), 
//							resultSet.getInt("FK_master_id"), 
//							resultSet.getString("master"));
					seance = mapRow(resultSet);
					seances.add(seance);
				} while (resultSet.next());
			}
			
		}catch (SQLException e) {
			throw new DaoSQLException(e);
		}
		
		return seances;
	}

	@Override
	public SeanceEntity findById(Integer id) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean delete(Integer id) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean delete(SeanceEntity entity) {
		throw new UnsupportedOperationException();
	}

	@Override
	public SeanceEntity create(SeanceEntity entity) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean update(SeanceEntity entity) {
		throw new UnsupportedOperationException();
	}

	@Override
	protected SeanceEntity mapRow(ResultSet resultSet) throws DaoSQLException {
		SeanceEntity seance = null;
		try {
			Date date = null;
			Timestamp timestamp = resultSet.getTimestamp("date");
			if (timestamp != null)
			    date = new Date(timestamp.getTime());
			
			seance = new SeanceEntity(resultSet.getInt("id"), 
					date, 
					resultSet.getByte("duration_hours"), 
					resultSet.getBigDecimal("cost_per_hour"), 
					resultSet.getInt("FK_account_id"), 
					resultSet.getString("client"), 
					resultSet.getInt("FK_master_id"), 
					resultSet.getString("master"));
		} catch (SQLException e) {
			throw new DaoSQLException(e);
		}
		return seance;
	}

}
