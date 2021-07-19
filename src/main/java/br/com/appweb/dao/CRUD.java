package br.com.appweb.dao;

import java.sql.SQLException;
import java.util.HashMap;

import java.sql.Connection;

import br.com.appweb.util.BEAN_CRUD;
import br.com.appweb.util.BEAN_PAGINATION;

public interface CRUD<T> {
	BEAN_PAGINATION getPagination(HashMap<String, Object> parameters, Connection con)throws SQLException;
	BEAN_PAGINATION getPagination(HashMap<String, Object> parameters)throws SQLException;
	BEAN_CRUD add(T obj, HashMap<String, Object> parameters)throws SQLException;
	BEAN_CRUD update(T obj, HashMap<String, Object> parameters)throws SQLException;
	BEAN_CRUD remove(Integer id, HashMap<String, Object> parameters)throws SQLException;
	
}
