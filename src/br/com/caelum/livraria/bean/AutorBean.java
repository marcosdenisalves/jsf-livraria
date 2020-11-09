package br.com.caelum.livraria.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.caelum.livraria.dao.AutorDao;
import br.com.caelum.livraria.modelo.Autor;
import br.com.caelum.livraria.tx.Transacional;
import br.com.caelum.livraria.util.RedirectView;

@Named
@ViewScoped
public class AutorBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private Autor autor = new Autor();
	private Integer autorId;
	
	@Inject
	private AutorDao dao;
	
	public Autor getAutor() {
		return autor;
	}
	
	public void setAutor(Autor autor) {
		this.autor = autor;
	}

	public Integer getAutorId() {
		return autorId;
	}

	public void setAutorId(Integer autorId) {
		this.autorId = autorId;
	}
	
	public void carregarAutorPelaId() {
		this.autor = dao.buscaPorId(autorId);
	}

	public List<Autor> getAutores(){
		return dao.listaTodos();
	}
	
	@Transacional
	public void remover(Autor autor){
		dao.remove(autor);
	}

	@Transacional
	public RedirectView gravar() {
		if (this.getAutor().getId() == null) {
			dao.adiciona(this.getAutor());
			this.setAutor(new Autor());
		} else {
			dao.atualiza(this.getAutor());
		}
		return new RedirectView("livro");
	}
}
