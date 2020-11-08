package br.com.caelum.livraria.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;

import br.com.caelum.livraria.dao.VendaDao;
import br.com.caelum.livraria.modelo.Venda;

@Named
@ViewScoped
public class VendasBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private LineChartModel model;

	@Inject
	VendaDao vendaDao;
	
	public LineChartModel getVendasModel() {

		model = new LineChartModel ();
		model.setAnimate(true);
		model.setTitle("Hístorico de Vendas");
		model.setLegendPosition("e");
		model.setShowPointLabels(true);
		model.getAxes().put(AxisType.X, new CategoryAxis("Vendas"));
		Axis yAxis = model.getAxis(AxisType.Y);
		yAxis.setMin(0);
		yAxis.setMax(1500);
		
		ChartSeries vendaSerie = new ChartSeries();
		vendaSerie.setLabel("Vendas 2020");

		List<Venda> vendas = getVendas();
		for (Venda venda : vendas) {
			vendaSerie.set(venda.getLivro().getTitulo(), venda.getQuantidade());
		}

		model.addSeries(vendaSerie);
		return model;
	}

	public List<Venda> getVendas() {
		return vendaDao.listaTodos();
	}
}
