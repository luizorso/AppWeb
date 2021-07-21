package br.com.appweb.api;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.google.gson.Gson;

import br.com.appweb.dao.CategoriaDAO;
import br.com.appweb.dao.impl.CategoriaDAOImpl;
import br.com.appweb.model.Categoria;
import br.com.appweb.util.BEAN_CRUD;
import br.com.appweb.util.BEAN_PAGINATION;

/**
 * Servlet implementation class CategoriaAPI
 */
@WebServlet(name="CategoriaAPI", urlPatterns = {"/categorias"})
public class CategoriaAPI extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private static final Logger LOG = Logger.getLogger(CategoriaAPI.class.getName());
    
	@Resource(name= "jdbc/appweb")
	private DataSource pool;
	
	private Gson jsonParse;
	private HashMap<String, Object> parameters;
	private String jsonResponse;
	private String action;
	private CategoriaDAO categoriaDAO;
    
	
	@Override
	public void init() throws ServletException {
		this.jsonParse = new Gson();
		this.parameters = new HashMap<>();
		this.categoriaDAO = new CategoriaDAOImpl(this.pool);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	/*protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
			try {
				if(this.pool.getConnection() != null) {
					LOG.info("CONEXÃO EFETUADA COM SUCESSO!");
					System.out.println("CONEXÃO EFETUADA COM SUCESSO!");
				}else {
					LOG.info("FALHA NA CONEXÃO!");
					System.out.println("FALHA NA CONEXÃO!");
				}
			} catch (SQLException e) {
				 Logger.getLogger(CategoriaAPI.class.getName()).log(Level.SEVERE, null, e);
				e.printStackTrace();
			}
		}
	*/
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.action = request.getParameter("action") == null ? "" : request.getParameter("action");
		try {
			switch (this.action) {
			case "paginarCategoria":
				/*BEAN_PAGINATION beanPagination = 
					this.categoriaDAO.getPagination(getParameters(request));
				BEAN_CRUD beanCrud = new BEAN_CRUD(beanPagination);
				*/
				processCategoria(new BEAN_CRUD(this.categoriaDAO.getPagination(getParameters(request))), response);
				break;
			case "addCategoria":
				processCategoria(this.categoriaDAO.add(getCategoria(request), getParameters(request)), response);
				break;
			case "updateCategoria":
				processCategoria(this.categoriaDAO.update(getCategoria(request), getParameters(request)), response);
				break;
			case "deleteCategoria":
				processCategoria(this.categoriaDAO.remove(Integer.parseInt(request.getParameter("idCategoria")), getParameters(request)), response);
				break;
			default:
				request.getRequestDispatcher("/pages/admin/categoria.jsp").forward(request, response);
				break;
			}
		} catch (SQLException ex) {
			Logger.getLogger(CategoriaAPI.class.getName()).log(Level.SEVERE, null, ex);
			ex.printStackTrace();
		}
	}
	
	private void processCategoria(BEAN_CRUD beanCrud, HttpServletResponse response) {
		try {
			this.jsonResponse = this.jsonParse.toJson(beanCrud);
			LOG.info(this.jsonResponse);
			response.setContentType("application/json");
			response.getWriter().write(this.jsonResponse);
		} catch (IOException ex) {
			Logger.getLogger(CategoriaAPI.class.getName()).log(Level.SEVERE, null, ex);
			ex.printStackTrace();
		}
	}
	
	private HashMap<String, Object> getParameters(HttpServletRequest request) {
		this.parameters.clear();
		this.parameters.put("FILTER", request.getParameter("nome"));
		this.parameters.put("SQL_ORDER_BY", "nome ASC");
		this.parameters.put("SQL_LIMIT",
				" LIMIT" + request.getParameter("sizePageCategoria") + " OFFSET "
						+ (Integer.parseInt(request.getParameter("numberPageCategoria"))
								- 1 * Integer.parseInt(request.getParameter("sizePageCategoria"))));
		return this.parameters;
	}
	
	private Categoria getCategoria(HttpServletRequest request) {
		Categoria categoria = new Categoria();
		if(request.getParameter("action").equals("updateCategoria")) {
			categoria.setIdCategoria(Integer.parseInt("idCategoria"));
		}
		categoria.setNome(request.getParameter("nome"));
		return categoria;
		
	}
	
	
}
