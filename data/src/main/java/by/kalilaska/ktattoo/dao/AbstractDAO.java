package by.kalilaska.ktattoo.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;

import by.kalilaska.ktattoo.dataexception.DaoSQLException;

public abstract class AbstractDAO<K extends Number, T> {
	protected Connection connection;	
	
	public AbstractDAO() {		
	}

	public abstract List<T> findAll() throws DaoSQLException;
	public abstract T findById(K id) throws DaoSQLException;
	public abstract boolean delete(K id) throws DaoSQLException;
	public abstract boolean delete(T entity);
	public abstract T create(T entity) throws DaoSQLException;
	public abstract boolean update(T entity) throws DaoSQLException;
	
	void setConnection(Connection connection) {
		this.connection = connection;
	}
	
	protected abstract T mapRow(ResultSet resultSet) throws DaoSQLException;
	
	
//	public void close() {
//		if(connection != null) {
//			try {
//				connection.close();
//			} catch (SQLException e) {
//				//LOG
//			}
//		}
//	}
}
