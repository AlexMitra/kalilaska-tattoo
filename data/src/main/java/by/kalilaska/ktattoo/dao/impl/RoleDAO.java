package by.kalilaska.ktattoo.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import by.kalilaska.ktattoo.dao.AbstractDAO;
import by.kalilaska.ktattoo.dataexception.SQLDataException;
import by.kalilaska.ktattoo.dataname.daoNameList;
import by.kalilaska.ktattoo.entity.RoleEntity;

public class RoleDAO extends AbstractDAO<Byte, RoleEntity>{
	
	private static final String SQL_SELECT_ALL_ROLE = "SELECT * FROM `role`;";
	
	public List<RoleEntity> findAll() throws SQLDataException {
		LinkedList<RoleEntity> roles = null;
		RoleEntity role = null;
		
		try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL_ROLE)){
			ResultSet resultSet = statement.executeQuery();
			if(resultSet.next()) {
				roles = new LinkedList<>();
				do {
					role = mapRow(resultSet);
					roles.add(role);
				}while(resultSet.next());
			}
		}catch (SQLException e) {
			throw new SQLDataException(e);
		}
		return roles;
		
	}

	@Override
	public boolean delete(Byte id) {
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
	protected RoleEntity mapRow(ResultSet resultSet) throws SQLDataException {
		RoleEntity role = null;		
		try {
			role = new RoleEntity();
			role.setId(resultSet.getByte(daoNameList.ROLE_DAO_RESULTSET_GET_ID));
			role.setName(resultSet.getString(daoNameList.ROLE_DAO_RESULTSET_GET_NAME));
		} catch (SQLException e) {
			throw new SQLDataException(e);
		}		
		
		return role;
	}

}
