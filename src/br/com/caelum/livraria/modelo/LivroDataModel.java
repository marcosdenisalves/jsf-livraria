package br.com.caelum.livraria.modelo;

import org.primefaces.model.LazyDataModel;

import br.com.caelum.livraria.dao.DAO;

public class LivroDataModel extends LazyDataModel<Livro> {

	private static final long serialVersionUID = 1L;
	
	private DAO<?> dao;
	
	public LivroDataModel() {
		super.setRowCount(dao.quantidadeDeElementos());
	}
}
