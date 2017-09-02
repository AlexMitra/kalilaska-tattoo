package by.kalilaska.ktattoo.service;

/**
 * Created by lovcov on 23.07.2017.
 */
public interface BaseService <T, K> {

	T convertEntityToBean(K entity);
}
