package br.com.appweb.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import javax.sql.DataSource;



import br.com.appweb.dao.CategoriaDAO;
import br.com.appweb.dao.SQLCloseable;
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
	public BEAN_PAGINATION getPagination(HashMap<String, Object> parameters, Connection con) 
			throws SQLException {
		BEAN_PAGINATION beanPagination = new BEAN_PAGINATION();
		List<Categoria> lista = new ArrayList<>();
		
		String sql= "SELECT COUNT(idCategoria) AS COUNT FROM categoria WHERE nome like CONCAT('%',?,'%')";
		PreparedStatement pstmt;
		ResultSet rs;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, String.valueOf(parameters.get("FILTER")));
			LOG.info(pstmt.toString());
			rs = pstmt.executeQuery();
			while(rs.next()) {
				beanPagination.setCOUNT_FILTER(rs.getInt("COUNT"));
				if(rs.getInt("COUNT") > 0) {
					sql = "SELECT * FROM categoria WHERE nome LIKE CONCAT('%', ?, '%') " +
						  "ORDER BY " + String.valueOf(parameters.get("SQL_ORDER_BY")) +
						  " " + String.valueOf(parameters.get("SQL_LIMIT"));
					pstmt = con.prepareStatement(sql);
					pstmt.setString(1, String.valueOf(parameters.get("FILTER")));
					LOG.info(pstmt.toString());
					rs = pstmt.executeQuery();
					while(rs.next()) {
						Categoria c = new Categoria();
						c.setIdCategoria(rs.getInt("idCategoria"));
						c.setNome(rs.getString("nome"));
						lista.add(c);
						
					}
				}
			}
			beanPagination.setLIST(lista);
			rs.close();
			pstmt.close();
			
		} catch (SQLException e) {
			System.out.print("Falha na Paginação: " + e.getMessage());
			e.printStackTrace();	
		}
		return beanPagination;
	}

	@Override
	public BEAN_PAGINATION getPagination(HashMap<String, Object> parameters) 
			throws SQLException {
		BEAN_PAGINATION beanPagination = null;
		
		try (Connection con = this.pool.getConnection()){
			beanPagination = getPagination(parameters, con);
			
			
		} catch (SQLException e) {
			System.out.println("Falha ao listar os registros da base de dados:" 
					+ e.getMessage());
			e.printStackTrace();
		}
		
		return beanPagination;
	}

	@Override
	public BEAN_CRUD add(Categoria obj, HashMap<String, Object> parameters)
			throws SQLException {
		BEAN_CRUD beanCrud = new BEAN_CRUD();
		String sql = "SELECT COUNT(idCategoria) AS COUNT FROM categoria WHERE nome = ?";
		PreparedStatement pstmt;
		ResultSet rs;
		try(Connection con = this.pool.getConnection(); 
				SQLCloseable fim = con::rollback;) {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, obj.getNome());
			LOG.info(pstmt.toString());
			rs = pstmt.executeQuery();
			while(rs.next()) {
				if(rs.getInt("COUNT") == 0) {
					sql = "INSERT INTO categoria (nome) VALUES (?)";
					pstmt = con.prepareStatement(sql);
					pstmt.setString(1, obj.getNome());
					LOG.info(pstmt.toString());
					pstmt.executeUpdate();
					con.commit();
					beanCrud.setMESSAGE_SERVER("ok");
					beanCrud.setBEAN_PAGINATION(getPagination(parameters, con));
				}else {
					beanCrud.setMESSAGE_SERVER("Já existe uma categoria com este nome na base de dados!");
					
				}
			}
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			System.out.println("Falha ao salvar o registro na base de dados: " 
					+ e.getMessage());
			e.printStackTrace();
		}
		
		return beanCrud;
	}

	@Override
	public BEAN_CRUD update(Categoria obj, HashMap<String, Object> parameters)
			throws SQLException {
		BEAN_CRUD beanCrud = new BEAN_CRUD();
		String sql = "SELECT COUNT(idCategoria) AS COUNT FROM categoria " + 
					 "WHERE nome = ? AND idCategoria != ?";
		PreparedStatement pstmt;
		ResultSet rs;
		try(Connection con = this.pool.getConnection(); 
				SQLCloseable fim = con::rollback;) {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, obj.getNome());
			pstmt.setInt(2, obj.getIdCategoria());
			LOG.info(pstmt.toString());
			rs = pstmt.executeQuery();
			while(rs.next()) {
				if(rs.getInt("COUNT") == 0) {
					sql = "UPDATE categoria set nome = ? WHERE idCategoria = ?";
					pstmt = con.prepareStatement(sql);
					pstmt.setString(1, obj.getNome());
					pstmt.setInt(2, obj.getIdCategoria());
					LOG.info(pstmt.toString());
					pstmt.executeUpdate();
					con.commit();
					beanCrud.setMESSAGE_SERVER("ok");
					beanCrud.setBEAN_PAGINATION(getPagination(parameters, con));
				}else {
					beanCrud.setMESSAGE_SERVER("Já existe uma categoria com este nome na base de dados!");
					
				}
			}
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			System.out.println("Falha ao atualizar o registro da base de dados: " 
					+ e.getMessage());
		}
		
		return beanCrud;
	}

	@Override
	public BEAN_CRUD remove(Integer id, HashMap<String, Object> parameters)
			throws SQLException {
		BEAN_CRUD beanCrud = new BEAN_CRUD();
		String sql = "DELETE FROM categoria WHERE idCategoria = ?";
		PreparedStatement pstmt;
		try(Connection con = this.pool.getConnection();
				SQLCloseable fim = con::rollback;) {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, id);
			LOG.info(pstmt.toString());
			pstmt.executeUpdate();
			con.commit();
			beanCrud.setMESSAGE_SERVER("ok");
			beanCrud.setBEAN_PAGINATION(getPagination(parameters, con));
			
		} catch (SQLException e) {
			System.out.println("Falha ao excluir o registro da base de dados: " 
					+ e.getMessage());
		}
		return beanCrud;
	}

}
