package by.kalilaska.ktattoo.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import by.kalilaska.ktattoo.dao.AbstractDAO;
import by.kalilaska.ktattoo.dataexception.SQLDataException;
import by.kalilaska.ktattoo.dataname.daoNameList;
import by.kalilaska.ktattoo.entity.TattooStyleEntity;

public class TattooStyleDAO extends AbstractDAO<Short, TattooStyleEntity> {
	
	private final static String SQL_SELECT_TATTOO_STYLE_BY_NAME = 
			"SELECT `s`.`id`, `s`.`name`, `s`.`description` " + 
			"FROM `tattoo_style` AS `s` " + 
			"WHERE `s`.`name` = ?;";
	
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
	
	private final static String SQL_INSERT_NEW_TATTOO_STYLE = 
			"INSERT INTO `tattoo_style` (`name`, `description`) VALUES " + 
			"(?, ?);";
	
	public TattooStyleEntity findTattooStyleByName(String name) throws SQLDataException {
		TattooStyleEntity style = null;
		
		try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_TATTOO_STYLE_BY_NAME)){
			statement.setString(1, name);
			ResultSet resultSet = statement.executeQuery();
			if(resultSet.next()) {
				style = mapRow(resultSet);
			}
		} catch (SQLException e) {
			throw new SQLDataException(e);
		}
		
		return style;
	}

	public List<TattooStyleEntity> findAllTattooStyleByMasterId(int id) throws SQLDataException{
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
			throw new SQLDataException(e);
		}
		return styles;
	}	
	
	public List<TattooStyleEntity> findAll() throws SQLDataException {
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
			throw new SQLDataException(e);
		}
		return styles;
	}	
	
	public boolean insertMasterTattooStyle(int masterId, List<String> styleIdList) throws SQLDataException{
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
					throw new SQLDataException(e);
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
	public boolean delete(Short id) throws SQLDataException {
		throw new UnsupportedOperationException();
	}
	
	public boolean cleanAllMasterTattooStyle(int masterId) throws SQLDataException {		
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
			throw new SQLDataException(e);
		}
		return result;
	}

	@Override
	public TattooStyleEntity create(TattooStyleEntity entity) throws SQLDataException {
		String name = entity.getName();
		String description = entity.getDescription();
		
		try (PreparedStatement statement = connection.prepareStatement(SQL_INSERT_NEW_TATTOO_STYLE)){
			statement.setString(1, name);
			statement.setString(2, description);
			int updateCount = statement.executeUpdate();
			
			if(updateCount > 0) {
				entity = findTattooStyleByName(name);
			}else {
				entity = null;
			}
		} catch (SQLException e) {
			throw new SQLDataException(e);
		}
		return entity;
	}

	@Override
	public boolean update(TattooStyleEntity entity) throws SQLDataException {
		throw new UnsupportedOperationException();
	}

	@Override
	protected TattooStyleEntity mapRow(ResultSet resultSet) throws SQLDataException {
		TattooStyleEntity style = null;
		
		style = new TattooStyleEntity();
		try {
			style.setId(resultSet.getShort(daoNameList.STYLE_DAO_RESULTSET_GET_ID));
			style.setName(resultSet.getString(daoNameList.STYLE_DAO_RESULTSET_GET_NAME));
			style.setDescription(resultSet.getString(daoNameList.STYLE_DAO_RESULTSET_GET_DESCRIPTION));
		} catch (SQLException e) {
			throw new SQLDataException(e);
		}
		
		return style;
	}
}
