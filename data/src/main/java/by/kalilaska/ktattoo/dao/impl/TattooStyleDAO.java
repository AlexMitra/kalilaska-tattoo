package by.kalilaska.ktattoo.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import by.kalilaska.ktattoo.dao.AbstractDAO;
import by.kalilaska.ktattoo.dataexception.DaoSQLException;
import by.kalilaska.ktattoo.entity.TattooStyleEntity;

public class TattooStyleDAO extends AbstractDAO<Short, TattooStyleEntity> {
	
	private final static String SQL_SELECT_ALL_TATTOO_STYLE_BY_MASTER_ID = 
			"SELECT `s`.`id`, `s`.`name`, `s`.`description` " + 
			"FROM `tattoo_style` AS `s` " + 
		    "INNER JOIN `tattoo_master_has_style` AS `tmhs` ON `s`.`id` = `tmhs`.`FK_style_id` " + 
		    "INNER JOIN `tattoo_master_info` AS `tmi` ON `tmhs`.`FK_master_id` = `tmi`.`id` " + 
		    "WHERE `tmi`.`id` = ?;";
	
	private final static String SQL_SELECT_ALL_TATTOO_STYLE = 
			"SELECT `s`.`id`, `s`.`name`, `s`.`description` " + 
			"FROM `tattoo_style` AS `s`;";
	
	private final static String SQL_CLEAN_ALL_MASTER_TATTOO_STYLE = 
			"DELETE FROM `tattoo_master_has_style` WHERE `tattoo_master_has_style`.`FK_master_id` = ?;";
	
	private final static String SQL_INSERT_MASTER_TATTOO_STYLE = 
			"INSERT INTO `tattoo_master_has_style` (`FK_master_id`, `FK_style_id`) VALUES " + 
			"(?, ?);";

	public List<TattooStyleEntity> findAllTattooStyleByMasterId(int id) throws DaoSQLException{
		LinkedList<TattooStyleEntity> styles = null;
		TattooStyleEntity style = null;
		
		try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL_TATTOO_STYLE_BY_MASTER_ID)){
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			if(resultSet.next()) {
				styles = new LinkedList<>();
				do {
					style = mapRow(resultSet);
					styles.add(style);
				}while(resultSet.next());
			}
		} catch (SQLException e) {
			throw new DaoSQLException(e);
		}
		return styles;
	}
	
	@Override
	public List<TattooStyleEntity> findAll() throws DaoSQLException {
		LinkedList<TattooStyleEntity> styles = null;
		TattooStyleEntity style = null;
		
		try(PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL_TATTOO_STYLE)) {
			ResultSet resultSet = statement.executeQuery();
			if(resultSet.next()) {
				styles = new LinkedList<>();
				do {
					style = mapRow(resultSet);
					styles.add(style);
				}while(resultSet.next());
			}
		}catch (SQLException e) {
			throw new DaoSQLException(e);
		}
		return styles;
	}

	@Override
	public TattooStyleEntity findById(Short id) {
		throw new UnsupportedOperationException();
	}	
	
	public boolean insertMasterTattooStyle(int masterId, List<String> styleIdList) throws DaoSQLException{
		boolean result = false;
		int summUpdateCount = 0;
		if(styleIdList != null) {
			for (String styleId : styleIdList) {
				try(PreparedStatement statement = connection.prepareStatement(SQL_INSERT_MASTER_TATTOO_STYLE)) {
					statement.setInt(1, masterId);
					statement.setString(2, styleId);
					int updateCount = statement.executeUpdate();
					
					if(updateCount > 0) {
						summUpdateCount++;
					}					
				} catch (SQLException e) {
					// LOG
					throw new DaoSQLException(e);
				}
			}
			
			if(summUpdateCount > 0 && !styleIdList.isEmpty()) {
				result = true;
			}
			if(styleIdList.isEmpty()) {
				result = true;
			}
		}		
		
		return result;
	}

	@Override
	public boolean delete(Short id) throws DaoSQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean delete(TattooStyleEntity entity) {
		throw new UnsupportedOperationException();
	}
	
	public boolean cleanAllMasterTattooStyle(int masterId) throws DaoSQLException {		
		List<TattooStyleEntity> masterStyleList = findAllTattooStyleByMasterId(masterId);
		if(masterStyleList == null || masterStyleList.isEmpty()) {
			return true;
		}
		
		boolean result = false;
		try(PreparedStatement statement = connection.prepareStatement(SQL_CLEAN_ALL_MASTER_TATTOO_STYLE)) {
			statement.setInt(1, masterId);
			int updateCount = statement.executeUpdate();
			if(updateCount > 0) {
				result = true;
			}
		} catch (SQLException e) {
			//LOG
			throw new DaoSQLException(e);
		}
		return result;
	}

	@Override
	public TattooStyleEntity create(TattooStyleEntity entity) throws DaoSQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean update(TattooStyleEntity entity) throws DaoSQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	protected TattooStyleEntity mapRow(ResultSet resultSet) throws DaoSQLException {
		TattooStyleEntity style = null;
		
		style = new TattooStyleEntity();
		try {
			style.setId(resultSet.getShort("id"));
			style.setName(resultSet.getString("name"));
			style.setDescription(resultSet.getString("description"));
		} catch (SQLException e) {
			throw new DaoSQLException(e);
		}
		
		return style;
	}

}
