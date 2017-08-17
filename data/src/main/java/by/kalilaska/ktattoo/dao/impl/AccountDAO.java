package by.kalilaska.ktattoo.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import by.kalilaska.ktattoo.dao.AbstractDAO;
import by.kalilaska.ktattoo.dataexception.DaoSQLException;
import by.kalilaska.ktattoo.dataname.daoNameList;
import by.kalilaska.ktattoo.entity.AccountEntity;

public class AccountDAO extends AbstractDAO<Integer, AccountEntity>{

//	private static final String SQL_SELECT_ACCOUNT_BY_NAME =
//			 "SELECT * FROM `account` WHERE `account`.`name` = ?;";
	private final static String SQL_SELECT_ALL_ACCOUNT =
			 "SELECT `a`.`id`, `a`.`name`, `a`.`email`, `a`.`password`, `a`.`phone`, `a`.`photo_url`, " +
					 "`a`.`is_allowed`, `r`.`name` AS `role` " +
					 "FROM `account` AS `a` " +
					 "INNER JOIN `role` AS `r` ON `a`.`FK_role_id` = `r`.`id`;";
	
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
	
	private final static String SQL_FORBIDE_ACCOUNT_BY_ID = 
			"UPDATE `account` SET `account`.`is_allowed` = false WHERE `account`.`id` = ?;";
	
	private final static String SQL_ALLOW_ACCOUNT_BY_ID = 
			"UPDATE `account` SET `account`.`is_allowed` = true WHERE `account`.`id` = ?;";
	
	private final static String SQL_UPDATE_ACCOUNT_BY_ID = 
			"UPDATE `account` AS `a` " + 
					"SET `a`.`name` = ?, " + 
						"`a`.`email` = ?, " + 
						"`a`.`phone` = ?, " + 
						"`a`.`FK_role_id` = (SELECT `role`.`id` FROM `role` WHERE `role`.`name` = ?) " + 
			"WHERE `a`.`id` = ?;";
	
	private final static String SQL_DELETE_ACCOUNT_BY_ID = 
			"DELETE FROM `account` WHERE `account`.`id` = ?;";
	
	private final static String SQL_INSERT_ACCOUNT =
			"INSERT INTO `account` (`name`, `email`, `password`, `photo_url`, `is_allowed`, `FK_role_id`) VALUES " + 
			"(?, ?, ?, NULL, true, " + daoNameList.DEFAULT_ROLE_ID + ");";
	
	public AccountDAO() {		
	}

	@Override
	public List<AccountEntity> findAll() throws DaoSQLException {
		LinkedList<AccountEntity> accounts = null;
		AccountEntity account = null;
		
		try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL_ACCOUNT)){
			ResultSet resultSet = statement.executeQuery();
			if(resultSet.next()) {
				accounts = new LinkedList<>();
				
				do {
//					account = new AccountEntity();
//					account.setId(resultSet.getInt("id"));
//					account.setName(resultSet.getString("name"));
//					account.setEmail(resultSet.getString("email"));
//					account.setPassword(resultSet.getString("password"));
//					account.setPhone(resultSet.getString("phone"));
//					account.setPhotoURL(resultSet.getString("photo_url"));
//					account.setAllowed(resultSet.getBoolean("is_allowed"));
//					account.setRole(resultSet.getString("role"));
					account = mapRow(resultSet);
					accounts.add(account);					
				}while (resultSet.next());
			}

		}catch (SQLException e) {
			throw new DaoSQLException(e);
		}
		
		return accounts;
	}

	@Override
	public AccountEntity findById(Integer id) {
		throw new UnsupportedOperationException();
	}
	
//	public AccountEntity findAccountByName(String name) throws DaoSQLException {		
//		AccountEntity account = null;
//		
//		try(PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ACCOUNT_BY_NAME)) {
//			statement.setString(1, name);			
//			ResultSet resultSet = statement.executeQuery();			
//			
//				while (resultSet.next()) {
//					account = new AccountEntity();
//					account.setId(resultSet.getInt("id"));				
//					account.setName(resultSet.getString("name"));
//					account.setEmail(resultSet.getString("email"));
//					account.setPassword(resultSet.getString("password"));
//					account.setPhone(resultSet.getString("phone"));
//					account.setPhotoURL(resultSet.getString("photo_url"));
//					account.setAllowed(resultSet.getBoolean("is_allowed"));					
//				
//			}	
//		} catch (SQLException e) {
//			throw new DaoSQLException(e);
//		}
//		return account;
//	}
	
	public AccountEntity findAccountByName(String name) throws DaoSQLException {		
		AccountEntity account = null;
		
		try(PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ACCOUNT_BY_NAME)) {
			statement.setString(1, name);			
			ResultSet resultSet = statement.executeQuery();
			
				while (resultSet.next()) {
//					account = new AccountEntity();
//					account.setId(resultSet.getInt("id"));				
//					account.setName(resultSet.getString("name"));
//					account.setEmail(resultSet.getString("email"));
//					account.setPassword(resultSet.getString("password"));
//					account.setPhone(resultSet.getString("phone"));
//					account.setPhotoURL(resultSet.getString("photo_url"));
//					account.setAllowed(resultSet.getBoolean("is_allowed"));
//					account.setRole(resultSet.getString("role"));
					account = mapRow(resultSet);
			}	
		} catch (SQLException e) {
			throw new DaoSQLException(e);
		}		
		return account;
	}
	
	public List<AccountEntity> findAccountByNameOrEmail(String name, String email) throws DaoSQLException {		
		List<AccountEntity> accounts = null;
		AccountEntity account = null;
		
		try(PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ACCOUNT_BY_NAME_OR_EMAIL)) {
			statement.setString(1, name);
			statement.setString(2, email);
			ResultSet resultSet = statement.executeQuery();			
				
			if(resultSet.next()) {
				accounts = new LinkedList<>();					
				do {
//					account = new AccountEntity();
//					account.setId(resultSet.getInt("id"));
//					account.setName(resultSet.getString("name"));
//					account.setEmail(resultSet.getString("email"));
//					account.setPassword(resultSet.getString("password"));
//					account.setPhone(resultSet.getString("phone"));
//					account.setPhotoURL(resultSet.getString("photo_url"));
//					account.setAllowed(resultSet.getBoolean("is_allowed"));
//					account.setRole(resultSet.getString("role"));
					account = mapRow(resultSet);
					accounts.add(account);
				}while (resultSet.next());
			}
		} catch (SQLException e) {
			throw new DaoSQLException(e);
		}		
		return accounts;
	}
	
	public boolean forbideAccountById(Integer id) throws DaoSQLException {
		boolean result = false;
		if(id != null) {
			try(PreparedStatement statement = connection.prepareStatement(SQL_FORBIDE_ACCOUNT_BY_ID)) {
				statement.setString(1, id.toString());
				
				int updateCount = statement.executeUpdate();
				if(updateCount > 0) {
					result = true;
				}
				
			} catch (SQLException e) {
				throw new DaoSQLException(e);
			}
		}		
		return result;
	}
	
	public boolean allowAccountById(Integer id) throws DaoSQLException {
		boolean result = false;
		if(id != null) {
			try(PreparedStatement statement = connection.prepareStatement(SQL_ALLOW_ACCOUNT_BY_ID)) {
				statement.setString(1, id.toString());
				
				int updateCount = statement.executeUpdate();
				if(updateCount > 0) {
					result = true;
				}
			} catch (SQLException e) {
				throw new DaoSQLException(e);
			}
		}	
		return result;
	}

	@Override
	public boolean delete(Integer id) throws DaoSQLException {
		boolean result = false;
		if(id != null) {
			try(PreparedStatement statement = connection.prepareStatement(SQL_DELETE_ACCOUNT_BY_ID)) {
				statement.setString(1, id.toString());
				
				int updateCount = statement.executeUpdate();
				if(updateCount > 0) {
					result = true;
				}
			} catch (SQLException e) {
				throw new DaoSQLException(e);
			}
		}		
		return result;
	}

	@Override
	public boolean delete(AccountEntity entity) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public AccountEntity create(AccountEntity entity) throws DaoSQLException {		
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
			throw new DaoSQLException(e);
		}		
		return account;
	}

	@Override
	public boolean update(AccountEntity entity) throws DaoSQLException {
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
			throw new DaoSQLException(e);
		}
		
		return result;
	}

	@Override
	protected AccountEntity mapRow(ResultSet resultSet) throws DaoSQLException {
		AccountEntity account = null;
		
		try {
			account = new AccountEntity();
			account.setId(resultSet.getInt("id"));
			account.setName(resultSet.getString("name"));
			account.setEmail(resultSet.getString("email"));
			account.setPassword(resultSet.getString("password"));
			account.setPhone(resultSet.getString("phone"));
			account.setPhotoURL(resultSet.getString("photo_url"));
			account.setAllowed(resultSet.getBoolean("is_allowed"));
			account.setRole(resultSet.getString("role"));
		} catch (SQLException e) {
			throw new DaoSQLException(e);			
		}				
		
		return account;
	}

}
