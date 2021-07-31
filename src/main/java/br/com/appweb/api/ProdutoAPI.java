package br.com.appweb.api;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import com.google.gson.Gson;

import br.com.appweb.dao.ProdutoDAO;
import br.com.appweb.dao.impl.ProdutoDAOImpl;
import br.com.appweb.model.Categoria;
import br.com.appweb.model.Produto;
import br.com.appweb.util.BEAN_CRUD;

/**
 * Servlet implementation class ProdutoAPI
 */
@WebServlet(name="ProdutoAPI", urlPatterns = {"/produtos"})
public class ProdutoAPI extends HttpServlet {
	private static final Logger LOG = Logger.getLogger(ProdutoAPI.class.getName());
	
	@Resource(name= "jdbc/appweb")
	private DataSource pool;
	private Gson json;
	private String jsonResponse;
	private HashMap<String, Object> parameters;
	private String action;
	private HttpSession session;
	
	private ProdutoDAO produtoDAO;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProdutoAPI() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    
    public void init()throws ServletException{
    	Context initCtx;
		try {
			initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");
			pool = (DataSource) envCtx.lookup("jdbc/appweb");
		} catch (NamingException e) {
			
			e.printStackTrace();
		}
    	
       	super.init();
    	this.parameters = new HashMap<>();
    	this.json = new Gson();
    	this.produtoDAO = new ProdutoDAOImpl(this.pool);
	}
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    		throws ServletException, IOException{
    		try {
    			this.action = 
    					request.getParameter("action") == null ? "" : request.getParameter("action");
    		
    			switch (this.action) {
					case "paginarProduto" :
						processProduto(new BEAN_CRUD(
							this.produtoDAO.getPagination(getParameters(request))), response);
							break;
					case "addProduto" :
						processProduto(
							this.produtoDAO.add(getProduto(request), getParameters(request)), response);
							break;
					case "updateProduto" :
						processProduto(
							this.produtoDAO.update(getProduto(request), getParameters(request)), response);
						break;
					case "deleteProduto" :
						processProduto(
							this.produtoDAO.delete(Integer.parseInt(request.getParameter("txtIdProdutoER")), 
									getParameters(request)), response);
    				default:
    					request.getRequestDispatcher("/pages/admin/produto.jsp").forward(request, response);
    					break;
				}
    		}catch(SQLException e) {
    			Logger.getLogger(ProdutoAPI.class.getName()).log(Level.SEVERE, null, e);
    		}
    		
    	}
    
    /*
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    	throws ServletException, IOException{
    	try {
    		if(this.pool.getConnection() != null) {
    			LOG.info("Conexão efetuada com sucesso!");
    			
    		}else {
    			LOG.info("Falha na conexão!");
    		}
			
		} catch (SQLException e) {
			Logger.getLogger(ProdutoAPI.class.getName()).log(Level.SEVERE, null, e);
			e.printStackTrace();
		}
    }
    */
    
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
		processRequest(request, response);
	}
	
	private Produto getProduto(HttpServletRequest request) {
		Produto p = new Produto();
		Categoria c = new Categoria();
		c.setIdCategoria(Integer.parseInt(request.getParameter("cboCategoriaProdutoER")));
		if(request.getParameter("action").equals("updateProduto")) {
			p.setIdProduto(Integer.parseInt(request.getParameter("txtIdProdutoER")));
		}
		
		p.setNome(request.getParameter("txtNomeProdutoER"));
		p.setPreco(Double.parseDouble(request.getParameter("txtPrecoER")));
		p.setEstoque(Integer.parseInt(request.getParameter("txtEstoqueER")));
		p.setEstoqueMin(Integer.parseInt(request.getParameter("txtEstoqueMinER")));
		p.setEstoqueMax(Integer.parseInt(request.getParameter("txtEstoqueMaxER")));
		p.setCategoria(c);
		return p;
	}
	
	private HashMap<String, Object> getParameters(HttpServletRequest request){
		this.parameters.clear();
		this.parameters.put("FILTER", request.getParameter("txtNomeProduto"));
		this.parameters.put("SQL_ORDERS", "NOME ASC");
		this.parameters.put("SQL_LIMIT", " LIMIT " + request.getParameter("sizePageProduto") +
				" OFFSET " + (Integer.parseInt(request.getParameter("numberPageProduto")) - 1 ) * Integer.parseInt(request.getParameter("sizePageProduto")));
		return this.parameters;
	}
	
	private void processProduto(BEAN_CRUD beanCrud, HttpServletResponse response) {
		try {
			this.jsonResponse = this.json.toJson(beanCrud);
			LOG.info(this.jsonResponse);
			response.setContentType("application/json");
			response.getWriter().write(this.jsonResponse);
			
		} catch (IOException e) {
			Logger.getLogger(ProdutoAPI.class.getName()).log(Level.SEVERE, null, e);
		}
	}

}
