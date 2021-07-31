package br.com.appweb.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import javax.sql.DataSource;

import br.com.appweb.dao.ProdutoDAO;
import br.com.appweb.dao.SQLCloseable;
import br.com.appweb.model.Categoria;
import br.com.appweb.model.Produto;
import br.com.appweb.util.BEAN_CRUD;
import br.com.appweb.util.BEAN_PAGINATION;

public class ProdutoDAOImpl implements ProdutoDAO {
	
	private static final Logger LOG = Logger.getLogger(ProdutoDAOImpl.class.getName());
	private final DataSource pool;
	
	public ProdutoDAOImpl(DataSource pool) {
		this.pool = pool;
	}

	@Override
	public BEAN_PAGINATION getPagination(HashMap<String, Object> parameters, Connection con) throws SQLException {
		BEAN_PAGINATION beanPagination = new BEAN_PAGINATION();
		List<Produto> lista = new ArrayList<>();
		PreparedStatement pstmt;
		ResultSet rs;
		String sql = "SELECT COUNT(idProduto) AS COUNT FROM PRODUTO " + 
					 "WHERE LOWER(nome) LIKE CONCAT('%', ?, '%')";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, String.valueOf(parameters.get("FILTER")));
			LOG.info(pstmt.toString());
			rs = pstmt.executeQuery();
			while(rs.next()) {
				beanPagination.setCOUNT_FILTER(rs.getInt("COUNT"));
				if(rs.getInt("COUNT") > 0) {
					sql = "SELECT * FROM produto " +
						  "WHERE LOWER(nome) LIKE CONCAT ('%', ?, '%') " +
						  "ORDER BY " + String.valueOf(parameters.get("SQL_ORDERS")) + "" +
						  parameters.get("SQL_LIMIT");
					pstmt = con.prepareStatement(sql);
					pstmt.setString(1, String.valueOf(parameters.get("FILTER")));
					LOG.info(pstmt.toString());
					rs = pstmt.executeQuery();
					while(rs.next()) {
						Categoria c = new Categoria();
						c.setIdCategoria(rs.getInt("idCategoria"));
						c.setNome(rs.getString("nome"));
						Produto p = new Produto();
						p.setIdProduto(rs.getInt("idProduto"));
						p.setPreco(rs.getDouble("preco"));
						p.setEstoque(rs.getInt("estoque"));
						p.setEstoqueMin(rs.getInt("estoqueMin"));
						p.setEstoqueMax(rs.getInt("estoqueMax"));
						p.setCategoria(c);
						lista.add(p);
						
					}
				}
				
			}
			beanPagination.setLIST(lista);
			rs.close();
			pstmt.close();
			
		} catch (SQLException e) {
			System.out.print("Falha na Listagem de Produtos: " + e.getMessage());
			e.printStackTrace();
		}
		
		return beanPagination;
	}

	@Override
	public BEAN_PAGINATION getPagination(HashMap<String, Object> parameters) throws SQLException {
		BEAN_PAGINATION beanPagination = null;
		try(Connection con = pool.getConnection()) {
			beanPagination = getPagination(parameters, con);
			
		} catch (Exception e) {
			System.out.println("Falha ao listar os registros da base de dados:" 
					+ e.getMessage());
			e.printStackTrace();
		}
		return beanPagination;
	}

	@Override
	public BEAN_CRUD add(Produto obj, HashMap<String, Object> parameters) throws SQLException {
		BEAN_CRUD beanCrud = new BEAN_CRUD();
		PreparedStatement pstmt;
		ResultSet rs;
		String sql = "SELECT COUNT (idProduto) AS COUNT FROM produto WHERE nome = ? ";
		try(Connection con = this.pool.getConnection();
				SQLCloseable finish = con::rollback;) {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, obj.getNome());
			rs = pstmt.executeQuery();
			while(rs.next()) {
				if(rs.getInt("COUNT") == 0) {
					sql = "INSERT INTO produto (nome, preco, estoque, estoqueMin, estoqueMax, idCategoria) " +
						  "VALUES (?, ?, ?, ?, ?, ?)";
					pstmt.setString(1, obj.getNome());
					pstmt.setDouble(2, obj.getPreco());
					pstmt.setInt(3, obj.getEstoque());
					pstmt.setInt(4, obj.getEstoqueMin());
					pstmt.setInt(5, obj.getEstoqueMax());
					pstmt.setInt(6, obj.getCategoria().getIdCategoria());
					pstmt.executeUpdate();
					con.commit();
					beanCrud.setMESSAGE_SERVER("ok");
					beanCrud.setBEAN_PAGINATION(getPagination(parameters, con));
				}else {
					beanCrud.setMESSAGE_SERVER("Já existe um produto com este nome na base de dados!");
				}
			}
			pstmt.close();
			rs.close();
			
		} catch (Exception e) {
			System.out.println("Falha ao salvar o registro na base de dados: " 
					+ e.getMessage());
			e.printStackTrace();
		}
		return beanCrud;
	}

	@Override
	public BEAN_CRUD update(Produto obj, HashMap<String, Object> parameters) throws SQLException {
		BEAN_CRUD beanCrud = new BEAN_CRUD();
		PreparedStatement pstmt;
		ResultSet rs;
		String sql = "SELECT COUNT(idProduto) AS COUNT FROM produto " + 
					"WHERE nome = ? AND idProduto != ?";
		try (Connection con = this.pool.getConnection();
				SQLCloseable finish = con::rollback;) {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, obj.getNome());
			pstmt.setInt(2, obj.getIdProduto());
			rs = pstmt.executeQuery();
			while(rs.next()) {
				if(rs.getInt("COUNT") == 0) {
					sql = "UPDATE produto set nome = ?, preco = ?, estoque = ?, estoqueMin = ?, estoqueMax = ?, idCategoria = ? " +
							"WHERE idProduto = ?";
					pstmt = con.prepareStatement(sql);
					pstmt.setString(1, obj.getNome());
					pstmt.setDouble(2, obj.getPreco());
					pstmt.setInt(3, obj.getEstoque());
					pstmt.setInt(4, obj.getEstoqueMin());
					pstmt.setInt(5, obj.getEstoqueMax());
					pstmt.setInt(6, obj.getCategoria().getIdCategoria());
					pstmt.setInt(7, obj.getIdProduto());
					LOG.info(pstmt.toString());
					pstmt.executeUpdate();
					con.commit();
					beanCrud.setMESSAGE_SERVER("ok");
					beanCrud.setBEAN_PAGINATION(getPagination(parameters, con));
					
				}else {
					beanCrud.setMESSAGE_SERVER("Já existe um produto com este nome na base de dados!");
				}
			}
			
		} catch (Exception e) {
			System.out.println("Falha ao atualizar o registro na base de dados: " 
					+ e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public BEAN_CRUD delete(Integer id, HashMap<String, Object> parameters) throws SQLException {
		BEAN_CRUD beanCrud = new BEAN_CRUD();
		PreparedStatement pstmt;
		String sql = "DELETE FROM produto WHERE idProduto = ?";
		try (Connection con = this.pool.getConnection();
				SQLCloseable finish = con::rollback;){
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, id);
			LOG.info(pstmt.toString());
			pstmt.executeUpdate();
			con.commit();
			beanCrud.setMESSAGE_SERVER("ok");
			beanCrud.setBEAN_PAGINATION(getPagination(parameters, con));
			pstmt.close();
			
			
		} catch (SQLException e) {
			System.out.println("Falha ao excluir o registro da base de dados: " 
					+ e.getMessage());
			e.printStackTrace();
		}
		return beanCrud;
	}

}
