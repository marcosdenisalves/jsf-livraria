package br.com.caelum.livraria.bean;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.caelum.livraria.dao.AutorDao;
import br.com.caelum.livraria.dao.LivroDao;
import br.com.caelum.livraria.modelo.Autor;
import br.com.caelum.livraria.modelo.Livro;
import br.com.caelum.livraria.modelo.LivroDataModel;
import br.com.caelum.livraria.tx.Transacional;
import br.com.caelum.livraria.util.RedirectView;

@Named
@ViewScoped
public class LivroBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private Livro livro = new Livro();

	private Integer livroId;
	private Integer autorId;

	private List<Livro> livros;
	private List<String> generos = Arrays.asList("Romance", "Drama", "Ação", "Aventura", "Comédia");

	@Inject
	LivroDao livroDao;
	@Inject
	AutorDao autorDao;

	@Inject
	private LivroDataModel livroDataModel;

	public Integer getAutorId() {
		return autorId;
	}

	public void setAutorId(Integer autorId) {
		this.autorId = autorId;
	}

	public Integer getLivroId() {
		return livroId;
	}

	public void setLivroId(Integer livroId) {
		this.livroId = livroId;
	}

	public Livro getLivro() {
		return livro;
	}

	public void setLivro(Livro livro) {
		this.livro = livro;
	}

	public List<Livro> getLivros() {
		if (this.livros == null) {
			this.livros = livroDao.listaTodos();
		}
		return livros;
	}

	public List<String> getGeneros() {
		return generos;
	}

	public List<Autor> getAutores() {
		return autorDao.listaTodos();
	}

	public LivroDataModel getLivroDataModel() {
		return livroDataModel;
	}

	public void setLivroDataModel(LivroDataModel livroDataModel) {
		this.livroDataModel = livroDataModel;
	}

	public List<Autor> getAutoresDoLivro() {
		return this.getLivro().getAutores();
	}
	
	public void carregar(Livro livro) {
		this.livro = livroDao.buscaPorId(livro.getId());
	}

	public void carregarLivroPelaId() {
		this.livro = livroDao.buscaPorId(livroId);
	}

	public void gravarAutor() {
		Autor autor = autorDao.buscaPorId(this.autorId);
		this.getLivro().adicionaAutor(autor);
		System.out.println("Escrito por: " + autor.getNome());
	}
	
	@Transacional
	public void gravar() {
		if (getLivro().getId() == null) {
			livroDao.adiciona(this.getLivro());
			this.livros = livroDao.listaTodos();
		} else {
			livroDao.atualiza(this.getLivro());
		}

		this.setLivro(new Livro());
	}

	@Transacional
	public void remover(Livro livro) {
		livroDao.remove(livro);
	}

	public void removerAutorDoLivro(Autor autor) {
		this.getLivro().removeAutor(autor);
	}

	public RedirectView formAutor() {
		return new RedirectView("autor");
	}

	public void comecaComDigitoUm(FacesContext fc, UIComponent component, Object value) throws ValidatorException {

		String valor = value.toString();
		if (!valor.startsWith("1")) {
			throw new ValidatorException(new FacesMessage("ISBN deveria começar com 1"));
		}

	}

	public boolean precoEhMenor(Object valorColuna, Object filtroDigitado, Locale locale) { // java.util.Locale

		// tirando espaços do filtro
		String textoDigitado = (filtroDigitado == null) ? null : filtroDigitado.toString().trim();

		System.out.println("Filtrando pelo " + textoDigitado + ", Valor do elemento: " + valorColuna);

		// o filtro é nulo ou vazio?
		if (textoDigitado == null || textoDigitado.equals("")) {
			return true;
		}

		// elemento da tabela é nulo?
		if (valorColuna == null) {
			return false;
		}

		try {
			// fazendo o parsing do filtro para converter para Double
			Double precoDigitado = Double.valueOf(textoDigitado);
			Double precoColuna = (Double) valorColuna;

			// comparando os valores, compareTo devolve um valor negativo se o value é menor
			// do que o filtro
			return precoColuna.compareTo(precoDigitado) < 0;

		} catch (NumberFormatException e) {

			// usuario nao digitou um numero
			return false;
		}
	}
}
