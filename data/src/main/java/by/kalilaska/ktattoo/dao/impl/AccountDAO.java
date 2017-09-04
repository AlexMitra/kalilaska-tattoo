package by.kalilaska.ktattoo.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import by.kalilaska.ktattoo.dao.AbstractDAO;
import by.kalilaska.ktattoo.dataexception.SQLDataException;
import by.kalilaska.ktattoo.dataname.daoNameList;
import by.kalilaska.ktattoo.entity.AccountEntity;

public class AccountDAO extends AbstractDAO<Integer, AccountEntity>{	

	private final static String SQL_SELECT_ALL_ACCOUNT =
			 "SELECT `a`.`id`, `a`.`name`, `a`.`email`, `a`.`password`, `a`.`phone`, `a`.`photo_url`, " +
					 "`a`.`is_allowed`, `r`.`name` AS `role` " +
					 "FROM `account` AS `a` " +
					 "INNER JOIN `role` AS `r` ON `a`.`FK_role_id` = `r`.`id` ORDER BY `role`, `a`.`id`;";
	
	private final static String SQL_SELECT_ACCOUNT_BY_NAME =
			 "SELECT `a`.`id`, `a`.`name`, `a`.`email`, `a`.`password`, `a`.`phone`, `a`.`photo_url`, " +
					 "`a`.`is_allowed`, `r`.`name` AS `role` " +
					 "FROM `account` AS `a` " +
					 "INNER JOIN `role` AS `r` ON `a`.`FK_role_id` = `r`.`id` " +
					 "WHERE `a`.`name` = ?;";
	
	private final static String SQL_SELECT_ACCOUNT_BY_NAME_OR_EMAIL =
			 "SELECT `a`.`id`, `a`.`name`, `a`.`email`, `a`.`password`, `a`.`phone`, `a`.`photo_url`, " +
					 "`a`.`is_allowed`, `r`.`name` AS `role` " +
					 "FROM `account` AS `a` " +
					 "INNER JOIN `role` AS `r` ON `a`.`FK_role_id` = `r`.`id` " +
					 "WHERE `a`.`name` = ? OR `a`.`email` = ?;";
	
	private final static String SQL_SELECT_ACCOUNT_BY_NAME_OR_EMAIL_OR_PASS =
			 "SELECT `a`.`id`, `a`.`name`, `a`.`email`, `a`.`password`, `a`.`phone`, `a`.`photo_url`, " +
					 "`a`.`is_allowed`, `r`.`name` AS `role` " +
					 "FROM `account` AS `a` " +
					 "INNER JOIN `role` AS `r` ON `a`.`FK_role_id` = `r`.`id` " +
					 "WHERE `a`.`name` = ? OR `a`.`email` = ? OR `a`.`password` = ?;";
	
	private final static String SQL_SELECT_ACCOUNT_BY_PHOTO_URL =
			 "SELECT `a`.`id`, `a`.`name`, `a`.`email`, `a`.`password`, `a`.`phone`, `a`.`photo_url`, " +
					 "`a`.`is_allowed`, `r`.`name` AS `role` " +
					 "FROM `account` AS `a` " +
					 "INNER JOIN `role` AS `r` ON `a`.`FK_role_id` = `r`.`id` " +
					 "WHERE `a`.`photo_url` = ?;";
	
	private final static String SQL_FORBIDE_ACCOUNT_BY_ID = 
			"UPDATE `account` SET `account`.`is_allowed` = false WHERE `account`.`id` = ?;";
	
	private final static String SQL_ALLOW_ACCOUNT_BY_ID = 
			"UPDATE `account` SET `account`.`is_allowed` = true WHERE `account`.`id` = ?;";
	
	private final static String SQL_UPDATE_ACCOUNT_PHOTO_URL_BY_ID = 
			"UPDATE `account` SET `account`.`photo_url` = ? WHERE `account`.`id` = ?;";
	
	private final static String SQL_DELETE_ACCOUNT_PHOTO_URL_BY_ID = 
			"UPDATE `account` SET `account`.`photo_url` = NULL WHERE `account`.`id` = ?;";
	
	private final static String SQL_UPDATE_ACCOUNT_BY_ID = 
			"UPDATE `account` AS `a` " + 
					"SET `a`.`name` = ?, " + 
						"`a`.`email` = ?, " + 
						"`a`.`phone` = ?, " + 
						"`a`.`FK_role_id` = (SELECT `role`.`id` FROM `role` WHERE `role`.`name` = ?) " + 
			"WHERE `a`.`id` = ?;";
	
	private final static String SQL_UPDATE_ACCOUNT_WITH_PASS_BY_ID = 
			"UPDATE `account` AS `a` " + 
					"SET `a`.`name` = ?, " + 
						"`a`.`email` = ?, " + 
						"`a`.`phone` = ?, " + 
						"`a`.`password` = ?, " + 
						"`a`.`FK_role_id` = (SELECT `role`.`id` FROM `role` WHERE `role`.`name` = ?) " + 
			"WHERE `a`.`id` = ?;";
	
	private final static String SQL_DELETE_ACCOUNT_BY_ID = 
			"DELETE FROM `account` WHERE `account`.`id` = ?;";
	
	private final static String SQL_INSERT_ACCOUNT =
			"INSERT INTO `account` (`name`, `email`, `password`, `photo_url`, `is_allowed`, `FK_role_id`) VALUES " + 
			"(?, ?, ?, NULL, true, " + daoNameList.DEFAULT_ROLE_ID + ");";
	
	public List<AccountEntity> findAll() throws SQLDataException {
		LinkedList<AccountEntity> accounts = null;
		AccountEntity account = null;
		
		try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL_ACCOUNT)){
			ResultSet resultSet = statement.executeQuery();
			if(resultSet.next()) {
				accounts = new LinkedList<>();
				
				do {
					account = mapRow(resultSet);
					accounts.add(account);					
				}while (resultSet.next());
			}

		}catch (SQLException e) {
			throw new SQLDataException(e);
		}
		
		return accounts;
	}
	
	public AccountEntity findAccountByName(String name) throws SQLDataException {		
		AccountEntity account = null;
		
		try(PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ACCOUNT_BY_NAME)) {
			statement.setString(1, name);
			ResultSet resultSet = statement.executeQuery();
			
				while (resultSet.next()) {
					account = mapRow(resultSet);
			}	
		} catch (SQLException e) {
			throw new SQLDataException(e);
		}		
		return account;
	}
	
	public List<AccountEntity> findAccountByNameOrEmail(String name, String email) throws SQLDataException {		
		List<AccountEntity> accounts = null;
		AccountEntity account = null;
		
		try(PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ACCOUNT_BY_NAME_OR_EMAIL)) {
			statement.setString(1, name);
			statement.setString(2, email);
			ResultSet resultSet = statement.executeQuery();			
				
			if(resultSet.next()) {
				accounts = new LinkedList<>();					
				do {
					account = mapRow(resultSet);
					accounts.add(account);
				}while (resultSet.next());
			}
		} catch (SQLException e) {
			throw new SQLDataException(e);
		}		
		return accounts;
	}
	
	public List<AccountEntity> findAccountByNameOrEmailOrPass(String name, String email, String pass) throws SQLDataException {		
		List<AccountEntity> accounts = null;
		AccountEntity account = null;
		
		try(PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ACCOUNT_BY_NAME_OR_EMAIL_OR_PASS)) {
			statement.setString(1, name);
			statement.setString(2, email);
			statement.setString(3, pass);
			ResultSet resultSet = statement.executeQuery();			
				
			if(resultSet.next()) {
				accounts = new LinkedList<>();					
				do {
					account = mapRow(resultSet);
					accounts.add(account);
				}while (resultSet.next());
			}
		} catch (SQLException e) {
			throw new SQLDataException(e);
		}
		return accounts;
	}
	
	public List<AccountEntity> findAccountByPhotoUrl(String photoUrl) throws SQLDataException{
		List<AccountEntity> accounts = null;
		AccountEntity account = null;
		
		if(photoUrl != null) {			
			try(PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ACCOUNT_BY_PHOTO_URL)) {
				statement.setString(1, photoUrl);				
				ResultSet resultSet = statement.executeQuery();			
					
				if(resultSet.next()) {
					accounts = new LinkedList<>();					
					do {
						account = mapRow(resultSet);
						accounts.add(account);
					}while (resultSet.next());
				}
			} catch (SQLException e) {
				throw new SQLDataException(e);
			}
		}
		return accounts;
	}
	
	public boolean forbideAccountById(Integer id) throws SQLDataException {
		boolean result = false;
		if(id != null) {
			try(PreparedStatement statement = connection.prepareStatement(SQL_FORBIDE_ACCOUNT_BY_ID)) {
				statement.setString(1, id.toString());
				
				int updateCount = statement.executeUpdate();
				if(updateCount > 0) {
					result = true;
				}
				
			} catch (SQLException e) {
				throw new SQLDataException(e);
			}
		}		
		return result;
	}
	
	public boolean allowAccountById(Integer id) throws SQLDataException {
		boolean result = false;
		if(id != null) {
			try(PreparedStatement statement = connection.prepareStatement(SQL_ALLOW_ACCOUNT_BY_ID)) {
				statement.setString(1, id.toString());
				
				int updateCount = statement.executeUpdate();
				if(updateCount > 0) {
					result = true;
				}
			} catch (SQLException e) {
				throw new SQLDataException(e);
			}
		}	
		return result;
	}
	
	public boolean updatePhotoUrl(AccountEntity accountEntity) throws SQLDataException {
		boolean updated = false;
		if(accountEntity != null) {
			int id = accountEntity.getId();
			String photoUrl = accountEntity.getPhotoURL();
			
			try(PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_ACCOUNT_PHOTO_URL_BY_ID)) {
				statement.setString(1, photoUrl);
				statement.setInt(2, id);
				
				int updateCount = statement.executeUpdate();
				if(updateCount > 0) {
					updated = true;
				}
			} catch (SQLException e) {
				throw new SQLDataException(e);
			}
		}
		return updated;
	}
	
	public boolean deletePhotoUrl(Integer id) throws SQLDataException {
		boolean deleted = false;
		
		if(id != null) {			
			try(PreparedStatement statement = connection.prepareStatement(SQL_DELETE_ACCOUNT_PHOTO_URL_BY_ID)) {				
				statement.setInt(1, id);
				
				int updateCount = statement.executeUpdate();
				if(updateCount > 0) {
					deleted = true;
				}
			} catch (SQLException e) {
				throw new SQLDataException(e);
			}
		}
		return deleted;
	}

	@Override
	public boolean delete(Integer id) throws SQLDataException {
		boolean result = false;
		if(id != null) {
			try(PreparedStatement statement = connection.prepareStatement(SQL_DELETE_ACCOUNT_BY_ID)) {
				statement.setString(1, id.toString());
				
				int updateCount = statement.executeUpdate();
				if(updateCount > 0) {
					result = true;
				}
			} catch (SQLException e) {
				throw new SQLDataException(e);
			}
		}		
		return result;
	}
	
	@Override
	public AccountEntity create(AccountEntity entity) throws SQLDataException {		
		AccountEntity account = null;
		
		try(PreparedStatement statement = connection.prepareStatement(SQL_INSERT_ACCOUNT)) {
			statement.setString(1, entity.getName());
			statement.setString(2, entity.getEmail());
			statement.setString(3, entity.getPassword());			
			
			int updateCount = statement.executeUpdate();			
			
			if(updateCount > 0) {				
				account = findAccountByName(entity.getName());
			}
		} catch (SQLException e) {			
			throw new SQLDataException(e);
		}		
		return account;
	}

	@Override
	public boolean update(AccountEntity entity) throws SQLDataException {
		boolean result = false;
		try(PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_ACCOUNT_BY_ID)) {
			statement.setString(1, entity.getName());
			statement.setString(2, entity.getEmail());
			statement.setString(3, entity.getPhone());
			statement.setString(4, entity.getRole());
			statement.setString(5, String.valueOf(entity.getId()));
			
			int updateCount = statement.executeUpdate();
			
			if(updateCount > 0) {
				result = true;
			}
		} catch (SQLException e) {			
			throw new SQLDataException(e);
		}
		
		return result;
	}	
	
	public boolean updateWithPass(AccountEntity entity) throws SQLDataException {
		boolean result = false;
		try(PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_ACCOUNT_WITH_PASS_BY_ID)) {
			statement.setString(1, entity.getName());
			statement.setString(2, entity.getEmail());
			statement.setString(3, entity.getPhone());
			statement.setString(4, entity.getPassword());
			statement.setString(5, entity.getRole());
			statement.setString(6, String.valueOf(entity.getId()));
			
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
	protected AccountEntity mapRow(ResultSet resultSet) throws SQLDataException {
		AccountEntity account = null;
		
		try {
			account = new AccountEntity();
			account.setId(resultSet.getInt(daoNameList.ACCOUNT_DAO_RESULTSET_GET_ID));
			account.setName(resultSet.getString(daoNameList.ACCOUNT_DAO_RESULTSET_GET_NAME));
			account.setEmail(resultSet.getString(daoNameList.ACCOUNT_DAO_RESULTSET_GET_EMAIL));
			account.setPassword(resultSet.getString(daoNameList.ACCOUNT_DAO_RESULTSET_GET_PASS));
			account.setPhone(resultSet.getString(daoNameList.ACCOUNT_DAO_RESULTSET_GET_PHONE));
			account.setPhotoURL(resultSet.getString(daoNameList.ACCOUNT_DAO_RESULTSET_GET_PHOTO_URL));
			account.setAllowed(resultSet.getBoolean(daoNameList.ACCOUNT_DAO_RESULTSET_GET_IS_ALLOWED));
			account.setRole(resultSet.getString(daoNameList.ACCOUNT_DAO_RESULTSET_GET_ROLE));
		} catch (SQLException e) {
			throw new SQLDataException(e);			
		}				
		
		return account;
	}
}
