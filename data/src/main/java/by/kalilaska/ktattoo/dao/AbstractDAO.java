package by.kalilaska.ktattoo.dao;

import java.sql.Connection;
import java.sql.ResultSet;

import by.kalilaska.ktattoo.dataexception.SQLDataException;

public abstract class AbstractDAO<K extends Number, T> {
	protected Connection connection;	
	
	public AbstractDAO() {		
	}
	
	public abstract boolean delete(K id) throws SQLDataException;
	public abstract T create(T entity) throws SQLDataException;
	public abstract boolean update(T entity) throws SQLDataException;
	
	void setConnection(Connection connection) {
		this.connection = connection;
	}
	
	protected abstract T mapRow(ResultSet resultSet) throws SQLDataException;
}
