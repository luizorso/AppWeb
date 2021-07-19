package br.com.appweb.dao;

import java.sql.SQLException;

public interface SQLCloseable extends AutoCloseable {

	@Override
	public void close() throws SQLException;
		

	
}
