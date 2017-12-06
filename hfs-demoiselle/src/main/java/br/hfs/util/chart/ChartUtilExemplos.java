package br.hfs.util.chart;

import java.util.ArrayList;
import java.util.List;

import org.jfree.chart.JFreeChart;

import br.hfs.util.chart.params.AreaChartParams;
import br.hfs.util.chart.params.BarChartParams;
import br.hfs.util.chart.params.BoxAndWhiskerChartDados;
import br.hfs.util.chart.params.BoxAndWhiskerChartParams;
import br.hfs.util.chart.params.BubbleChartParams;
import br.hfs.util.chart.params.CandlestickChartParams;
import br.hfs.util.chart.params.CategoryDados;
import br.hfs.util.chart.params.GanttChartDados;
import br.hfs.util.chart.params.GanttChartParams;
import br.hfs.util.chart.params.HighLowChartParams;
import br.hfs.util.chart.params.HistogramChartParams;
import br.hfs.util.chart.params.LineChartParams;
import br.hfs.util.chart.params.OHLCSeriesDados;
import br.hfs.util.chart.params.OHLCSeriesParams;
import br.hfs.util.chart.params.PieChartDados;
import br.hfs.util.chart.params.PieChartParams;
import br.hfs.util.chart.params.ScatterChartParams;
import br.hfs.util.chart.params.SeriesDados;
import br.hfs.util.chart.params.StackedAreaChartParams;
import br.hfs.util.chart.params.StackedBarChartParams;
import br.hfs.util.chart.params.TimeSeriesChartParams;
import br.hfs.util.chart.params.TimeSeriesChartSerie;
import br.hfs.util.chart.params.XYAreaChartParams;
import br.hfs.util.chart.params.XYBarChartParams;
import br.hfs.util.chart.params.XYDados;
import br.hfs.util.chart.params.XYLineChartParams;
import br.hfs.util.chart.params.XYSeriesParams;
import br.hfs.util.chart.params.XYSeriesParamsDouble;
import br.hfs.util.chart.params.XYStepAreaChartParams;
import br.hfs.util.chart.params.XYStepChartParams;

public final class ChartUtilExemplos {

	public JFreeChart PieChartExemplo(ChartUtil chartUtil, boolean chart3D) {
		ArrayList<PieChartDados> dados = new ArrayList<PieChartDados>();
		dados.add(new PieChartDados("A", new Integer(75)));
		dados.add(new PieChartDados("B", new Integer(10)));
		dados.add(new PieChartDados("C", new Integer(10)));
		dados.add(new PieChartDados("D", new Integer(5)));
		PieChartParams params;
		if (!chart3D) {
			params = new PieChartParams(500, 300, "Distribuição HFS", true,
					true, false, false, dados);
			return chartUtil.criarPieChart(params);
		} else {
			params = new PieChartParams(500, 300, "Distribuição HFS", true,
					true, false, true, dados);
			return chartUtil.criarPieChart(params);
		}
	}

	public JFreeChart XYLineChartExample(ChartUtil chartUtil) {
		ArrayList<XYDados> dados1 = new ArrayList<XYDados>();
		dados1.add(new XYDados(1, 1));
		dados1.add(new XYDados(1, 2));
		dados1.add(new XYDados(2, 1));
		dados1.add(new XYDados(3, 9));
		dados1.add(new XYDados(4, 10));

		ArrayList<XYDados> dados2 = new ArrayList<XYDados>();
		dados2.add(new XYDados(3, 6));
		dados2.add(new XYDados(8, 9));
		dados2.add(new XYDados(7, 10));
		dados2.add(new XYDados(8, 1));
		dados2.add(new XYDados(9, 4));

		ArrayList<XYSeriesParams> series = new ArrayList<XYSeriesParams>();
		series.add(new XYSeriesParams("População1", dados1));
		series.add(new XYSeriesParams("População2", dados2));

		XYLineChartParams params = new XYLineChartParams(500, 300,
				"Gráfico XY Linha", true, true, false, "x-eixo", "y-eixo",
				true, series);
		return chartUtil.criarXYLineChart(params);
	}

	public JFreeChart BarChartExample(ChartUtil chartUtil, boolean chart3D) {
		ArrayList<CategoryDados> dados = new ArrayList<CategoryDados>();
		dados.add(new CategoryDados(6, "Lucro1", "Carlos"));
		dados.add(new CategoryDados(3, "Lucro2", "Carlos"));
		dados.add(new CategoryDados(7, "Lucro1", "Raul"));
		dados.add(new CategoryDados(10, "Lucro2", "Raul"));
		dados.add(new CategoryDados(8, "Lucro1", "Luiz"));
		dados.add(new CategoryDados(8, "Lucro2", "Luiz"));
		dados.add(new CategoryDados(5, "Lucro1", "Rafael"));
		dados.add(new CategoryDados(6, "Lucro2", "Rafael"));
		dados.add(new CategoryDados(12, "Lucro1", "Márcio"));
		dados.add(new CategoryDados(5, "Lucro2", "Márcio"));

		if (!chart3D) {
			BarChartParams params = new BarChartParams(500, 300,
					"Comparação entre vendedor", true, true, false, "Vendedor",
					"Lucro", true, dados, false);

			return chartUtil.criarBarChart(params);
		} else {
			BarChartParams params2 = new BarChartParams(500, 300,
					"Comparação entre vendedor", true, true, false, "Vendedor",
					"Valor ($)", true, dados, true);

			return chartUtil.criarBarChart(params2);
		}
	}

	public JFreeChart TimeSeriesExample(ChartUtil chartUtil) {
		ArrayList<SeriesDados> dados1 = new ArrayList<SeriesDados>();
		dados1.add(new SeriesDados(10, 1, 2004, 100));
		dados1.add(new SeriesDados(10, 2, 2004, 150));
		dados1.add(new SeriesDados(10, 3, 2004, 250));
		dados1.add(new SeriesDados(10, 4, 2004, 275));
		dados1.add(new SeriesDados(10, 5, 2004, 325));
		dados1.add(new SeriesDados(10, 6, 2004, 425));

		ArrayList<SeriesDados> dados2 = new ArrayList<SeriesDados>();
		dados2.add(new SeriesDados(20, 1, 2004, 200));
		dados2.add(new SeriesDados(20, 2, 2004, 250));
		dados2.add(new SeriesDados(20, 3, 2004, 450));
		dados2.add(new SeriesDados(20, 4, 2004, 475));
		dados2.add(new SeriesDados(20, 5, 2004, 125));

		ArrayList<TimeSeriesChartSerie> series = new ArrayList<TimeSeriesChartSerie>();
		series.add(new TimeSeriesChartSerie("População1", dados1));
		series.add(new TimeSeriesChartSerie("População2", dados2));

		TimeSeriesChartParams params = new TimeSeriesChartParams(500, 300,
				"População da Cidade do Recife", true, true, false, "Data",
				"População", series);

		return chartUtil.criarTimeSeriesChart(params);
	}

	public JFreeChart AreaChartExample(ChartUtil chartUtil) {
		ArrayList<CategoryDados> dados = new ArrayList<CategoryDados>();
		dados.add(new CategoryDados(6, "Lucro1", "Carlos"));
		dados.add(new CategoryDados(3, "Lucro2", "Carlos"));
		dados.add(new CategoryDados(7, "Lucro1", "Raul"));
		dados.add(new CategoryDados(10, "Lucro2", "Raul"));
		dados.add(new CategoryDados(8, "Lucro1", "Luiz"));
		dados.add(new CategoryDados(8, "Lucro2", "Luiz"));
		dados.add(new CategoryDados(5, "Lucro1", "Rafael"));
		dados.add(new CategoryDados(6, "Lucro2", "Rafael"));
		dados.add(new CategoryDados(12, "Lucro1", "Márcio"));
		dados.add(new CategoryDados(5, "Lucro2", "Márcio"));

		AreaChartParams params = new AreaChartParams(500, 300,
				"Comparação entre vendedor", false, true, false, "Vendedor",
				"Lucro", true, dados);

		return chartUtil.criarAreaChart(params);
	}

	public JFreeChart StackedBarChartExample(ChartUtil chartUtil,
			boolean chart3D) {
		ArrayList<CategoryDados> dados = new ArrayList<CategoryDados>();
		dados.add(new CategoryDados(6, "Lucro1", "Carlos"));
		dados.add(new CategoryDados(3, "Lucro2", "Carlos"));
		dados.add(new CategoryDados(7, "Lucro1", "Raul"));
		dados.add(new CategoryDados(10, "Lucro2", "Raul"));
		dados.add(new CategoryDados(8, "Lucro1", "Luiz"));
		dados.add(new CategoryDados(8, "Lucro2", "Luiz"));
		dados.add(new CategoryDados(5, "Lucro1", "Rafael"));
		dados.add(new CategoryDados(6, "Lucro2", "Rafael"));
		dados.add(new CategoryDados(12, "Lucro1", "Márcio"));
		dados.add(new CategoryDados(5, "Lucro2", "Márcio"));

		if (!chart3D) {
			StackedBarChartParams params = new StackedBarChartParams(500, 300,
					"Comparação entre vendedor", true, true, false, "Vendedor",
					"Lucro", true, dados, false);

			return chartUtil.criarStackedBarChart(params);
		} else {
			StackedBarChartParams params2 = new StackedBarChartParams(500, 300,
					"Comparação entre vendedor", true, true, false, "Vendedor",
					"Valor ($)", true, dados, true);

			return chartUtil.criarStackedBarChart(params2);
		}
	}

	public JFreeChart XYAreaChartExample(ChartUtil chartUtil) {
		ArrayList<XYDados> dados1 = new ArrayList<XYDados>();
		dados1.add(new XYDados(1, 1));
		dados1.add(new XYDados(1, 2));
		dados1.add(new XYDados(2, 1));
		dados1.add(new XYDados(3, 9));
		dados1.add(new XYDados(4, 10));

		ArrayList<XYDados> dados2 = new ArrayList<XYDados>();
		dados2.add(new XYDados(3, 6));
		dados2.add(new XYDados(8, 9));
		dados2.add(new XYDados(7, 10));
		dados2.add(new XYDados(8, 1));
		dados2.add(new XYDados(9, 4));

		ArrayList<XYSeriesParams> series = new ArrayList<XYSeriesParams>();
		series.add(new XYSeriesParams("População1", dados1));
		series.add(new XYSeriesParams("População2", dados2));

		XYAreaChartParams params = new XYAreaChartParams(500, 300,
				"Gráfico XY Área", true, true, false, "x-eixo", "y-eixo", true,
				series);
		return chartUtil.criarXYAreaChart(params);
	}

	public JFreeChart XYStepChartExample(ChartUtil chartUtil) {
		ArrayList<XYDados> dados1 = new ArrayList<XYDados>();
		dados1.add(new XYDados(1, 1));
		dados1.add(new XYDados(1, 2));
		dados1.add(new XYDados(2, 1));
		dados1.add(new XYDados(3, 9));
		dados1.add(new XYDados(4, 10));

		ArrayList<XYDados> dados2 = new ArrayList<XYDados>();
		dados2.add(new XYDados(3, 6));
		dados2.add(new XYDados(8, 9));
		dados2.add(new XYDados(7, 10));
		dados2.add(new XYDados(8, 1));
		dados2.add(new XYDados(9, 4));

		ArrayList<XYSeriesParams> series = new ArrayList<XYSeriesParams>();
		series.add(new XYSeriesParams("População1", dados1));
		series.add(new XYSeriesParams("População2", dados2));

		XYStepChartParams params = new XYStepChartParams(500, 300,
				"Gráfico XY Step", true, true, false, "x-eixo", "y-eixo", true,
				series);
		return chartUtil.criarXYStepChart(params);
	}

	public JFreeChart XYStepAreaChartExample(ChartUtil chartUtil) {
		ArrayList<XYDados> dados1 = new ArrayList<XYDados>();
		dados1.add(new XYDados(1, 1));
		dados1.add(new XYDados(1, 2));
		dados1.add(new XYDados(2, 1));
		dados1.add(new XYDados(3, 9));
		dados1.add(new XYDados(4, 10));

		ArrayList<XYDados> dados2 = new ArrayList<XYDados>();
		dados2.add(new XYDados(3, 6));
		dados2.add(new XYDados(8, 9));
		dados2.add(new XYDados(7, 10));
		dados2.add(new XYDados(8, 1));
		dados2.add(new XYDados(9, 4));

		ArrayList<XYSeriesParams> series = new ArrayList<XYSeriesParams>();
		series.add(new XYSeriesParams("População1", dados1));
		series.add(new XYSeriesParams("População2", dados2));

		XYStepAreaChartParams params = new XYStepAreaChartParams(500, 300,
				"Gráfico XY Step Área", true, true, false, "x-eixo", "y-eixo",
				true, series);
		return chartUtil.criarXYStepAreaChart(params);
	}

	public JFreeChart LineChartExample(ChartUtil chartUtil, boolean chart3D) {
		ArrayList<CategoryDados> dados = new ArrayList<CategoryDados>();
		dados.add(new CategoryDados(6, "Lucro1", "Carlos"));
		dados.add(new CategoryDados(3, "Lucro2", "Carlos"));
		dados.add(new CategoryDados(7, "Lucro1", "Raul"));
		dados.add(new CategoryDados(10, "Lucro2", "Raul"));
		dados.add(new CategoryDados(8, "Lucro1", "Luiz"));
		dados.add(new CategoryDados(8, "Lucro2", "Luiz"));
		dados.add(new CategoryDados(5, "Lucro1", "Rafael"));
		dados.add(new CategoryDados(6, "Lucro2", "Rafael"));
		dados.add(new CategoryDados(12, "Lucro1", "Márcio"));
		dados.add(new CategoryDados(5, "Lucro2", "Márcio"));

		LineChartParams params;
		if (!chart3D) {
			params = new LineChartParams(500, 300, "Comparação entre vendedor",
					false, true, false, "Vendedor", "Lucro", true, dados, false);

			return chartUtil.criarLineChart(params);
		} else {
			params = new LineChartParams(500, 300, "Comparação entre vendedor",
					false, true, false, "Vendedor", "Lucro", true, dados, true);

			return chartUtil.criarLineChart(params);
		}
	}

	public JFreeChart BubbleChartExample(ChartUtil chartUtil) {
		ArrayList<XYSeriesParamsDouble> series = new ArrayList<XYSeriesParamsDouble>();
		series.add(new XYSeriesParamsDouble("População1", chartUtil
				.getRandomDados(3, 20, 100)));
		series.add(new XYSeriesParamsDouble("População2", chartUtil
				.getRandomDados(3, 20, 100)));

		BubbleChartParams params = new BubbleChartParams(500, 300,
				"Gráfico XY Bolha", true, true, false, "x-eixo", "y-eixo",
				true, series);

		return chartUtil.criarBubbleChart(params);
	}

	public JFreeChart HistogramChartExample(ChartUtil chartUtil) {
		ArrayList<XYSeriesParamsDouble> series = new ArrayList<XYSeriesParamsDouble>();
		series.add(new XYSeriesParamsDouble("População1", chartUtil
				.getRandomDados(6, 20, 100)));
		series.add(new XYSeriesParamsDouble("População2", chartUtil
				.getRandomDados(6, 20, 100)));

		HistogramChartParams params = new HistogramChartParams(500, 300,
				"Gráfico XY Histograma", true, true, false, "x-eixo", "y-eixo",
				true, series);

		return chartUtil.criarHistogramChart(params);
	}

	public JFreeChart XYBarChartExample(ChartUtil chartUtil, boolean chart3D) {
		ArrayList<XYSeriesParamsDouble> series = new ArrayList<XYSeriesParamsDouble>();
		series.add(new XYSeriesParamsDouble("População1", chartUtil
				.getRandomDados(6, 20, 100)));
		series.add(new XYSeriesParamsDouble("População2", chartUtil
				.getRandomDados(6, 20, 100)));

		XYBarChartParams params;
		if (!chart3D) {
			params = new XYBarChartParams(500, 300, "Gráfico XY Barra", true,
					true, false, "x-eixo", "y-eixo", true, series, false);

			return chartUtil.criarXYBarChart(params);
		} else {
			params = new XYBarChartParams(500, 300, "Gráfico XY Barra", true,
					true, false, "x-eixo", "y-eixo", true, series, true);
			return chartUtil.criarXYBarChart(params);
		}
	}

	public JFreeChart BoxAndWhiskerChartExample(ChartUtil chartUtil) {
		List<BoxAndWhiskerChartDados> dados = new ArrayList<BoxAndWhiskerChartDados>();
		List<Double> anexos = new ArrayList<Double>();
		anexos.add(new Double(34));
		anexos.add(new Double(24));
		anexos.add(new Double(14));
		anexos.add(new Double(6));
		anexos.add(new Double(50));
		dados.add(new BoxAndWhiskerChartDados(100, 25, 10, 8, 5, 30, 6, 80,
				anexos));

		BoxAndWhiskerChartParams params = new BoxAndWhiskerChartParams(500,
				300, "Comparação entre vendedor", true, "Vendedor", "Lucro",
				"ChaveLinha", "ChaveColuna", dados);

		return chartUtil.criarBoxAndWhiskerChart(params);
	}

	public JFreeChart StackedAreaChartExample(ChartUtil chartUtil) {
		ArrayList<CategoryDados> dados = new ArrayList<CategoryDados>();
		dados.add(new CategoryDados(6, "Lucro1", "Carlos"));
		dados.add(new CategoryDados(3, "Lucro2", "Carlos"));
		dados.add(new CategoryDados(7, "Lucro1", "Raul"));
		dados.add(new CategoryDados(10, "Lucro2", "Raul"));
		dados.add(new CategoryDados(8, "Lucro1", "Luiz"));
		dados.add(new CategoryDados(8, "Lucro2", "Luiz"));
		dados.add(new CategoryDados(5, "Lucro1", "Rafael"));
		dados.add(new CategoryDados(6, "Lucro2", "Rafael"));
		dados.add(new CategoryDados(12, "Lucro1", "Márcio"));
		dados.add(new CategoryDados(5, "Lucro2", "Márcio"));

		StackedAreaChartParams params = new StackedAreaChartParams(500, 300,
				"Comparação entre vendedor", true, true, false, "Vendedor",
				"Lucro", true, dados);

		return chartUtil.criarStackedAreaChart(params);
	}

	public JFreeChart ScatterChartExample(ChartUtil chartUtil) {
		ArrayList<XYDados> dados1 = new ArrayList<XYDados>();
		dados1.add(new XYDados(1, 1));
		dados1.add(new XYDados(1, 2));
		dados1.add(new XYDados(2, 1));
		dados1.add(new XYDados(3, 9));
		dados1.add(new XYDados(4, 10));

		ArrayList<XYDados> dados2 = new ArrayList<XYDados>();
		dados2.add(new XYDados(3, 6));
		dados2.add(new XYDados(8, 9));
		dados2.add(new XYDados(7, 10));
		dados2.add(new XYDados(8, 1));
		dados2.add(new XYDados(9, 4));

		ArrayList<XYSeriesParams> series = new ArrayList<XYSeriesParams>();
		series.add(new XYSeriesParams("População1", dados1));
		series.add(new XYSeriesParams("População2", dados2));

		ScatterChartParams params = new ScatterChartParams(500, 300,
				"Gráfico Scatter", true, true, false, "x-eixo", "y-eixo", true,
				series);
		return chartUtil.criarScatterChart(params);
	}

	public JFreeChart HighLowChartExample(ChartUtil chartUtil) {
		ArrayList<OHLCSeriesDados> dados1 = new ArrayList<OHLCSeriesDados>();
		dados1.add(new OHLCSeriesDados(10, 1, 2000, 100, 200, 150, 300));
		dados1.add(new OHLCSeriesDados(10, 2, 2000, 150, 50, 200, 140));
		dados1.add(new OHLCSeriesDados(10, 3, 2000, 250, 100, 80, 93));
		dados1.add(new OHLCSeriesDados(10, 4, 2000, 275, 230, 280, 168));
		dados1.add(new OHLCSeriesDados(10, 5, 2000, 325, 245, 123, 52));
		dados1.add(new OHLCSeriesDados(10, 6, 2000, 425, 45, 98, 195));

		ArrayList<OHLCSeriesDados> dados2 = new ArrayList<OHLCSeriesDados>();
		dados2.add(new OHLCSeriesDados(20, 1, 2004, 200, 168, 201, 91));
		dados2.add(new OHLCSeriesDados(20, 2, 2004, 250, 83, 99, 134));
		dados2.add(new OHLCSeriesDados(20, 3, 2004, 450, 187, 321, 123));
		dados2.add(new OHLCSeriesDados(20, 4, 2004, 475, 92, 181, 123));
		dados2.add(new OHLCSeriesDados(20, 5, 2004, 125, 345, 214, 23));

		ArrayList<OHLCSeriesParams> series = new ArrayList<OHLCSeriesParams>();
		series.add(new OHLCSeriesParams("População1", dados1));
		series.add(new OHLCSeriesParams("População2", dados2));

		HighLowChartParams params = new HighLowChartParams(500, 300,
				"População da Cidade do Recife", true, true, false, "Data",
				"População", "Chave", series);

		return chartUtil.criarHighLowChart(params);
	}

	public JFreeChart CandlestickChartExample(ChartUtil chartUtil) {
		ArrayList<OHLCSeriesDados> dados1 = new ArrayList<OHLCSeriesDados>();
		dados1.add(new OHLCSeriesDados(10, 1, 2004, 100, 200, 150, 300));
		dados1.add(new OHLCSeriesDados(10, 2, 2004, 150, 50, 200, 140));
		dados1.add(new OHLCSeriesDados(10, 3, 2004, 250, 100, 80, 93));
		dados1.add(new OHLCSeriesDados(10, 4, 2004, 275, 230, 280, 168));
		dados1.add(new OHLCSeriesDados(10, 5, 2004, 325, 245, 123, 52));
		dados1.add(new OHLCSeriesDados(10, 6, 2004, 425, 45, 98, 195));

		ArrayList<OHLCSeriesDados> dados2 = new ArrayList<OHLCSeriesDados>();
		dados2.add(new OHLCSeriesDados(20, 1, 2004, 200, 168, 201, 91));
		dados2.add(new OHLCSeriesDados(20, 2, 2004, 250, 83, 99, 134));
		dados2.add(new OHLCSeriesDados(20, 3, 2004, 450, 187, 321, 123));
		dados2.add(new OHLCSeriesDados(20, 4, 2004, 475, 92, 181, 123));
		dados2.add(new OHLCSeriesDados(20, 5, 2004, 125, 345, 214, 23));

		ArrayList<OHLCSeriesParams> series = new ArrayList<OHLCSeriesParams>();
		series.add(new OHLCSeriesParams("População1", dados1));
		series.add(new OHLCSeriesParams("População2", dados2));

		CandlestickChartParams params = new CandlestickChartParams(500, 300,
				"População da Cidade do Recife", true, true, false, "Data",
				"População", "Chave", series);

		return chartUtil.criarCandlestickChart(params);
	}

	public JFreeChart GanttChartExample(ChartUtil chartUtil) {

		Number[] partidas1 = new Number[6];
		partidas1[0] = 100;
		partidas1[1] = 150;
		partidas1[2] = 250;
		partidas1[3] = 275;
		partidas1[4] = 325;
		partidas1[5] = 425;

		Number[] chegadas1 = new Number[6];
		chegadas1[0] = 200;
		chegadas1[1] = 250;
		chegadas1[2] = 450;
		chegadas1[3] = 475;
		chegadas1[4] = 125;
		chegadas1[5] = 305;

		Number[] chegadas2 = new Number[6];
		chegadas2[0] = 100;
		chegadas2[1] = 150;
		chegadas2[2] = 250;
		chegadas2[3] = 275;
		chegadas2[4] = 325;
		chegadas2[5] = 425;

		Number[] partidas2 = new Number[6];
		partidas2[0] = 200;
		partidas2[1] = 250;
		partidas2[2] = 450;
		partidas2[3] = 475;
		partidas2[4] = 125;
		partidas2[5] = 305;

		GanttChartDados[] series = new GanttChartDados[2];
		series[0] = new GanttChartDados("População1", partidas1, chegadas1);
		series[1] = new GanttChartDados("População2", partidas2, chegadas2);

		GanttChartParams params = new GanttChartParams(500, 300,
				"Comparação entre vendedor", true, true, false, "Vendedor",
				"Lucro", series);

		return chartUtil.criarGanttChart(params);
	}

}
