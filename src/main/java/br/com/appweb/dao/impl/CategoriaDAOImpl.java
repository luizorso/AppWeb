package br.com.appweb.dao.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Logger;

import javax.sql.DataSource;

import com.mysql.jdbc.Connection;

import br.com.appweb.dao.CategoriaDAO;
import br.com.appweb.model.Categoria;
import br.com.appweb.util.BEAN_CRUD;
import br.com.appweb.util.BEAN_PAGINATION;

public class CategoriaDAOImpl implements CategoriaDAO {
	private static final Logger LOG = Logger.getLogger(CategoriaDAOImpl.class.getName());
	private final DataSource pool;
	
	public CategoriaDAOImpl(DataSource pool) {
		this.pool = pool;
		
	}

	@Override
	public BEAN_PAGINATION getPagination(HashMap<String, Object> parameters, Connection con) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BEAN_PAGINATION getPagination(HashMap<String, Object> parameters) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BEAN_CRUD add(Categoria obj, HashMap<String, Object> parameters) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BEAN_CRUD update(Categoria obj, HashMap<String, Object> parameters) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BEAN_CRUD remove(Integer id, HashMap<String, Object> parameters) {
		// TODO Auto-generated method stub
		return null;
	}

}
