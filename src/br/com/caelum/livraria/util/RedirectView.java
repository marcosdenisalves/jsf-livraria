package br.com.caelum.livraria.util;

public class RedirectView {
	
	private String viewName;

	public RedirectView(String viewName) {
		this.setViewName(viewName);
	}

	public String getViewName() {
		return viewName;
	}

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}

	@Override
	public String toString() {
		return viewName + "?faces-redirect=true";
	}
}
