package br.com.appweb.util;

public class BEAN_CRUD {
	private String MESSAGE_SERVER;
	private BEAN_PAGINATION BEAN_PAGINATION;
	
	public BEAN_CRUD() {
		
	}

	public String getMESSAGE_SERVER() {
		return this.MESSAGE_SERVER;
	}

	public void setMESSAGE_SERVER(String MESSAGE_SERVER) {
		this.MESSAGE_SERVER = MESSAGE_SERVER;
	}

	public BEAN_PAGINATION getBEAN_PAGINATION() {
		return this.BEAN_PAGINATION;
	}

	public void setBEAN_PAGINATION(BEAN_PAGINATION BEAN_PAGINATION) {
		this.BEAN_PAGINATION = BEAN_PAGINATION;
	}
	
	

}
