package by.kalilaska.ktattoo.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import by.kalilaska.ktattoo.dao.AbstractDAO;
import by.kalilaska.ktattoo.dataexception.DaoSQLException;
import by.kalilaska.ktattoo.entity.RoleEntity;

public class RoleDAO extends AbstractDAO<Byte, RoleEntity>{
	
	private static final String SQL_SELECT_ROLES_BY_ACCOUNT_NAME =
	"SELECT `r`.`id`, `r`.`name`, `r`.`is_allowed` FROM `role` AS `r` " + 
			"INNER JOIN `account_has_role` ON `r`.`id` = `account_has_role`.`FK_role_id` " + 
			"INNER JOIN `account` AS `a` ON `account_has_role`.`FK_account_id` = `a`.`id` " + 
			"WHERE `a`.`id` = ?;";
	
	private static final String SQL_SELECT_ALL_ROLE = "SELECT * FROM `role`;";

	public RoleDAO() {		
	}

	@Override
	public List<RoleEntity> findAll() throws DaoSQLException {
		LinkedList<RoleEntity> roles = null;
		RoleEntity role = null;
		
		try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL_ROLE)){
			ResultSet resultSet = statement.executeQuery();
			if(resultSet.next()) {
				roles = new LinkedList<>();
				do {
//					role = new RoleEntity();
//					role.setId(resultSet.getByte("id"));
//					role.setName(resultSet.getString("name"));
					role = mapRow(resultSet);
					roles.add(role);
				}while(resultSet.next());
			}
		}catch (SQLException e) {
			throw new DaoSQLException(e);
		}
		return roles;
		
	}

	@Override
	public RoleEntity findById(Byte id) {
		throw new UnsupportedOperationException();
	}	
	
	public List<RoleEntity> findRolesByAccountId(int id) throws DaoSQLException {
		RoleEntity role = null;
		LinkedList<RoleEntity> roles = null;
		try(PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ROLES_BY_ACCOUNT_NAME)){
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			
			roles = new LinkedList<>();
			while(resultSet.next()) {				
//				role = new RoleEntity();
//				role.setId(resultSet.getByte("id"));
//				role.setName(resultSet.getString("name"));
				role = mapRow(resultSet);
				roles.add(role);
			}
		}catch (SQLException e) {
			throw new DaoSQLException(e);
		}
		return roles;
	}

	@Override
	public boolean delete(Byte id) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean delete(RoleEntity entity) {
		throw new UnsupportedOperationException();
	}

	@Override
	public RoleEntity create(RoleEntity entity) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean update(RoleEntity entity) {
		throw new UnsupportedOperationException();
	}

	@Override
	protected RoleEntity mapRow(ResultSet resultSet) throws DaoSQLException {
		RoleEntity role = null;
		
		try {
			role = new RoleEntity();
			role.setId(resultSet.getByte("id"));
			role.setName(resultSet.getString("name"));
		} catch (SQLException e) {
			throw new DaoSQLException(e);
		}		
		
		return role;
	}

}
