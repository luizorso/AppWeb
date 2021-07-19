package br.com.appweb.util;

import java.util.List;

public class BEAN_PAGINATION {
	private Integer COUNT_FILTER;
	private List<?> LIST;
	
	public BEAN_PAGINATION() {
		
	}

	public Integer getCOUNT_FILTER() {
		return COUNT_FILTER;
	}

	public void setCOUNT_FILTER(Integer cOUNT_FILTER) {
		COUNT_FILTER = cOUNT_FILTER;
	}

	public List<?> getLIST() {
		return LIST;
	}

	public void setLIST(List<?> lIST) {
		LIST = lIST;
	}
	
	

}
