package br.com.caelum.livraria.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;

import br.com.caelum.livraria.dao.DAO;
import br.com.caelum.livraria.modelo.Livro;
import br.com.caelum.livraria.modelo.Venda;

@Named
@ViewScoped
public class VendasBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private LineChartModel model;
	
	public LineChartModel getVendasModel() {

		model = new LineChartModel ();
		model.setAnimate(true);
		model.setTitle("Hístorico de Vendas");
		model.setLegendPosition("e");
		model.setShowPointLabels(true);
		model.getAxes().put(AxisType.X, new CategoryAxis("Vendas"));
		Axis yAxis = model.getAxis(AxisType.Y);
		yAxis.setMin(0);
		yAxis.setMax(500);
		
		ChartSeries vendaSerie2020 = new ChartSeries();
		vendaSerie2020.setLabel("Vendas 2020");

		List<Venda> vendas = getVendas(1234);
		for (Venda venda : vendas) {
			vendaSerie2020.set(venda.getLivro().getTitulo(), venda.getQuantidade());
		}

		ChartSeries vendaSerie2015 = new ChartSeries();
		vendaSerie2015.setLabel("Vendas 2015");
		
		vendas = getVendas(4321);
		for (Venda venda : vendas) {
			vendaSerie2015.set(venda.getLivro().getTitulo(), venda.getQuantidade());
		}
		
		model.addSeries(vendaSerie2015);
		model.addSeries(vendaSerie2020);

		return model;
	}

	public List<Venda> getVendas(long valor) {

		List<Livro> livros = new DAO<Livro>(Livro.class).listaTodos();
		List<Venda> vendas = new ArrayList<Venda>();

		Random random = new Random(valor);

		for (Livro livro : livros) {

			Integer quantidade = random.nextInt(500);

			vendas.add(new Venda(livro, quantidade));
		}
		return vendas;
	}
}
