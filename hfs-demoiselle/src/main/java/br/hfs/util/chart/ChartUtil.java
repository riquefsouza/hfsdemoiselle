package br.hfs.util.chart;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.logging.Logger;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.category.DefaultIntervalCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.statistics.BoxAndWhiskerItem;
import org.jfree.data.statistics.DefaultBoxAndWhiskerCategoryDataset;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.ohlc.OHLCSeries;
import org.jfree.data.time.ohlc.OHLCSeriesCollection;
import org.jfree.data.xy.DefaultIntervalXYDataset;
import org.jfree.data.xy.DefaultXYZDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

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

public final class ChartUtil {
	private static Logger log = Logger.getLogger("ChartUtil");

	public double[][] getRandomDados(int maxLinhas, int maxCols,
			int fatorMultiplicacao) {
		double[][] dados = new double[maxLinhas][maxCols];
		for (int linha = 0; linha < maxLinhas; linha++) {
			for (int col = 0; col < maxCols; col++) {
				dados[linha][col] = (RandomUtils.nextDouble() * fatorMultiplicacao);
			}
		}
		return dados;
	}

	public void gerarGrafico(HttpServletResponse res, String arquivoChartJPG)
			throws ChartException {
		try {
			byte[] jpg = null;
			if (arquivoChartJPG != null) {
				File arq = new File(arquivoChartJPG);
				jpg = FileUtils.readFileToByteArray(arq);

				// Diz que vai enviar um arquivo do tipo JPG
				res.setContentType("image/jpeg");

				// Diz o tamanho dos dados
				res.setContentLength(jpg.length);

				// Escreve no browser
				ServletOutputStream ouputStream = res.getOutputStream();
				ouputStream.write(jpg, 0, jpg.length);
				ouputStream.flush();
				ouputStream.close();

				// excluir arquivo temporario
				arq.delete();
			}
		} catch (IOException e) {
			throw new ChartException(log,
					"Erro de entrada/saída ao gerar gráfico(chart)");
		}
	}

	public JFreeChart criarPieChart(PieChartParams params) {
		DefaultPieDataset pieDataset = new DefaultPieDataset();
		for (PieChartDados dado : params.getDados()) {
			pieDataset.setValue(dado.getRotulo(), dado.getDado());
		}
		JFreeChart chart;
		if (params.isUsarPie3D()) {
			chart = ChartFactory.createPieChart3D(params.getTitulo(), // Title
					pieDataset, // Dataset
					params.isMostrarLegenda(), // Show legend
					params.isUsarTooltips(), // Use tooltips
					params.isGerarURLs() // Configure chart to generate URLs?
					);
		} else {
			chart = ChartFactory.createPieChart(params.getTitulo(), // Title
					pieDataset, // Dataset
					params.isMostrarLegenda(), // Show legend
					params.isUsarTooltips(), // Use tooltips
					params.isGerarURLs() // Configure chart to generate URLs?
					);
		}
		return chart;
	}

	public JFreeChart criarXYLineChart(XYLineChartParams params) {
		XYSeries series;
		XYSeriesCollection dataset = new XYSeriesCollection();
		for (XYSeriesParams serie : params.getSeries()) {
			series = new XYSeries(serie.getNomeSerie());
			for (XYDados dado : serie.getDados()) {
				series.add(dado.getSerieX(), dado.getSerieY());
			}
			dataset.addSeries(series);
		}
		JFreeChart chart = ChartFactory.createXYLineChart(params.getTitulo(), // Title
				params.getRotuloXAxis(), // x-axis Label
				params.getRotuloYAxis(), // y-axis Label
				dataset, // Dataset
				(params.isOrientacaoPlotVertical() ? PlotOrientation.VERTICAL
						: PlotOrientation.HORIZONTAL), // Plot Orientation
				params.isMostrarLegenda(), // Show Legend
				params.isUsarTooltips(), // Use tooltips
				params.isGerarURLs() // Configure chart to generate URLs?
				);

		// It is possible to modify the graph to show each of the 5 data points:
		// if (bMarkDataPoints) {
		// XYItemRenderer rend = chart.getXYPlot().getRenderer();
		// StandardXYItemRenderer rr = (StandardXYItemRenderer) rend;
		// rr.setPlotLines(true);
		//
		// try {
		// ChartUtilities.saveChartAsJPEG(new File(
		// "C:\\XYChartExample_MarkDataPoints.jpg"), chart, 500, 300);
		// } catch (IOException e) {
		// System.err.println("Problem occurred creating chart.");
		// }
		// } else {
		// }
		return chart;
	}

	public JFreeChart criarBarChart(BarChartParams params)
		{
		// Create a simple Bar chart
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for (CategoryDados dado : params.getDados()) {
			dataset.setValue(dado.getValor(), dado.getChaveLinha(),
					dado.getChaveColuna());
		}
		JFreeChart chart;
		if (params.isUsarBarra3D()) {
			chart = ChartFactory
					.createBarChart3D(
							params.getTitulo(), // Title
							params.getRotuloCategoriaAxis(),
							params.getRotuloValorAxis(),
							dataset, // Dataset
							(params.isOrientacaoPlotVertical() ? PlotOrientation.VERTICAL
									: PlotOrientation.HORIZONTAL), // Plot
							// Orientation
							params.isMostrarLegenda(), // Show Legend
							params.isUsarTooltips(), // Use tooltips
							params.isGerarURLs() // Configure chart to generate
					// URLs?
					);
		} else {
			chart = ChartFactory
					.createBarChart(
							params.getTitulo(), // Title
							params.getRotuloCategoriaAxis(),
							params.getRotuloValorAxis(),
							dataset, // Dataset
							(params.isOrientacaoPlotVertical() ? PlotOrientation.VERTICAL
									: PlotOrientation.HORIZONTAL), // Plot
							// Orientation
							params.isMostrarLegenda(), // Show Legend
							params.isUsarTooltips(), // Use tooltips
							params.isGerarURLs() // Configure chart to generate
					// URLs?
					);
		}
		if (params.getCorParams() != null) {
			// Set the background colour of the chart
			chart.setBackgroundPaint(new Color(params.getCorParams()
					.getCorFundoChart().getRed(), params.getCorParams()
					.getCorFundoChart().getGreen(), params.getCorParams()
					.getCorFundoChart().getBlue()));
			// Adjust the colour of the title
			chart.getTitle().setPaint(
					new Color(params.getCorParams().getCorTituloChart()
							.getRed(), params.getCorParams()
							.getCorTituloChart().getGreen(), params
							.getCorParams().getCorTituloChart().getBlue()));
			// Get the Plot object for a bar graph
			CategoryPlot p = chart.getCategoryPlot();
			// Modify the plot background
			p.setBackgroundPaint(new Color(params.getCorParams()
					.getCorFundoPlot().getRed(), params.getCorParams()
					.getCorFundoPlot().getGreen(), params.getCorParams()
					.getCorFundoPlot().getBlue()));
			// Modify the colour of the plot gridlines
			p.setRangeGridlinePaint(new Color(params.getCorParams()
					.getCorLinhasGridPlot().getRed(), params.getCorParams()
					.getCorLinhasGridPlot().getGreen(), params.getCorParams()
					.getCorLinhasGridPlot().getBlue()));
		}

		return chart;
	}

	public JFreeChart criarTimeSeriesChart(TimeSeriesChartParams params) {
		// Create a time series chart
		TimeSeries pop;
		TimeSeriesCollection dataset = new TimeSeriesCollection();

		for (TimeSeriesChartSerie serie : params.getSeries()) {
			pop = new TimeSeries(serie.getNomeSerie());

			for (SeriesDados dado : serie.getDados()) {
				pop.add(new Day(dado.getDia(), dado.getMes(), dado.getAno()),
						dado.getValor());
			}
			dataset.addSeries(pop);
		}

		JFreeChart chart = ChartFactory.createTimeSeriesChart(
				params.getTitulo(), // Title
				params.getRotuloTempoAxis(), params.getRotuloValorAxis(),
				dataset, // Dataset
				params.isMostrarLegenda(), // Show Legend
				params.isUsarTooltips(), // Use tooltips
				params.isGerarURLs() // Configure chart to generate URLs?
				);

		XYPlot plot = chart.getXYPlot();
		DateAxis axis = (DateAxis) plot.getDomainAxis();
		axis.setDateFormatOverride(new SimpleDateFormat("dd/MM/yyyy"));

		return chart;
	}

	public JFreeChart criarAreaChart(AreaChartParams params) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for (CategoryDados dado : params.getDados()) {
			dataset.setValue(dado.getValor(), dado.getChaveLinha(),
					dado.getChaveColuna());
		}

		JFreeChart chart = ChartFactory.createAreaChart(params.getTitulo(),
				params.getRotuloCategoriaAxis(), params.getRotuloValorAxis(),
				dataset,
				(params.isOrientacaoPlotVertical() ? PlotOrientation.VERTICAL
						: PlotOrientation.HORIZONTAL), params
						.isMostrarLegenda(), params.isUsarTooltips(), params
						.isGerarURLs());

		return chart;
	}

	public JFreeChart criarStackedBarChart(StackedBarChartParams params) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for (CategoryDados dado : params.getDados()) {
			dataset.setValue(dado.getValor(), dado.getChaveLinha(),
					dado.getChaveColuna());
		}

		JFreeChart chart;
		if (params.isUsarBarra3D()) {
			chart = ChartFactory
					.createStackedBarChart3D(
							params.getTitulo(), // Title
							params.getRotuloCategoriaAxis(),
							params.getRotuloValorAxis(),
							dataset, // Dataset
							(params.isOrientacaoPlotVertical() ? PlotOrientation.VERTICAL
									: PlotOrientation.HORIZONTAL), // Plot
							// Orientation
							params.isMostrarLegenda(), // Show Legend
							params.isUsarTooltips(), // Use tooltips
							params.isGerarURLs() // Configure chart to generate
					// URLs?
					);
		} else {
			chart = ChartFactory
					.createStackedBarChart(
							params.getTitulo(), // Title
							params.getRotuloCategoriaAxis(),
							params.getRotuloValorAxis(),
							dataset, // Dataset
							(params.isOrientacaoPlotVertical() ? PlotOrientation.VERTICAL
									: PlotOrientation.HORIZONTAL), // Plot
							// Orientation
							params.isMostrarLegenda(), // Show Legend
							params.isUsarTooltips(), // Use tooltips
							params.isGerarURLs() // Configure chart to generate
					// URLs?
					);
		}

		return chart;
	}

	public JFreeChart criarXYAreaChart(XYAreaChartParams params) {
		XYSeries series;
		XYSeriesCollection dataset = new XYSeriesCollection();
		for (XYSeriesParams serie : params.getSeries()) {
			series = new XYSeries(serie.getNomeSerie());
			for (XYDados dado : serie.getDados()) {
				series.add(dado.getSerieX(), dado.getSerieY());
			}
			dataset.addSeries(series);
		}
		JFreeChart chart = ChartFactory.createXYAreaChart(params.getTitulo(), // Title
				params.getRotuloXAxis(), // x-axis Label
				params.getRotuloYAxis(), // y-axis Label
				dataset, // Dataset
				(params.isOrientacaoPlotVertical() ? PlotOrientation.VERTICAL
						: PlotOrientation.HORIZONTAL), // Plot Orientation
				params.isMostrarLegenda(), // Show Legend
				params.isUsarTooltips(), // Use tooltips
				params.isGerarURLs() // Configure chart to generate URLs?
				);

		return chart;
	}

	public JFreeChart criarXYStepChart(XYStepChartParams params) {
		XYSeries series;
		XYSeriesCollection dataset = new XYSeriesCollection();
		for (XYSeriesParams serie : params.getSeries()) {
			series = new XYSeries(serie.getNomeSerie());
			for (XYDados dado : serie.getDados()) {
				series.add(dado.getSerieX(), dado.getSerieY());
			}
			dataset.addSeries(series);
		}
		JFreeChart chart = ChartFactory.createXYStepChart(params.getTitulo(), // Title
				params.getRotuloXAxis(), // x-axis Label
				params.getRotuloYAxis(), // y-axis Label
				dataset, // Dataset
				(params.isOrientacaoPlotVertical() ? PlotOrientation.VERTICAL
						: PlotOrientation.HORIZONTAL), // Plot Orientation
				params.isMostrarLegenda(), // Show Legend
				params.isUsarTooltips(), // Use tooltips
				params.isGerarURLs() // Configure chart to generate URLs?
				);

		return chart;
	}

	public JFreeChart criarXYStepAreaChart(XYStepAreaChartParams params) {
		XYSeries series;
		XYSeriesCollection dataset = new XYSeriesCollection();
		for (XYSeriesParams serie : params.getSeries()) {
			series = new XYSeries(serie.getNomeSerie());
			for (XYDados dado : serie.getDados()) {
				series.add(dado.getSerieX(), dado.getSerieY());
			}
			dataset.addSeries(series);
		}
		JFreeChart chart = ChartFactory.createXYStepAreaChart(params
				.getTitulo(), // Title
				params.getRotuloXAxis(), // x-axis Label
				params.getRotuloYAxis(), // y-axis Label
				dataset, // Dataset
				(params.isOrientacaoPlotVertical() ? PlotOrientation.VERTICAL
						: PlotOrientation.HORIZONTAL), // Plot Orientation
				params.isMostrarLegenda(), // Show Legend
				params.isUsarTooltips(), // Use tooltips
				params.isGerarURLs() // Configure chart to generate URLs?
				);

		return chart;
	}

	public JFreeChart criarLineChart(LineChartParams params) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for (CategoryDados dado : params.getDados()) {
			dataset.setValue(dado.getValor(), dado.getChaveLinha(),
					dado.getChaveColuna());
		}
		JFreeChart chart;
		if (params.isUsarBarra3D()) {
			chart = ChartFactory
					.createLineChart3D(
							params.getTitulo(),
							params.getRotuloCategoriaAxis(),
							params.getRotuloValorAxis(),
							dataset,
							(params.isOrientacaoPlotVertical() ? PlotOrientation.VERTICAL
									: PlotOrientation.HORIZONTAL), params
									.isMostrarLegenda(), params
									.isUsarTooltips(), params.isGerarURLs());
		} else {
			chart = ChartFactory
					.createLineChart(
							params.getTitulo(),
							params.getRotuloCategoriaAxis(),
							params.getRotuloValorAxis(),
							dataset,
							(params.isOrientacaoPlotVertical() ? PlotOrientation.VERTICAL
									: PlotOrientation.HORIZONTAL), params
									.isMostrarLegenda(), params
									.isUsarTooltips(), params.isGerarURLs());

		}

		return chart;
	}

	public JFreeChart criarBubbleChart(BubbleChartParams params) {
		DefaultXYZDataset dataset = new DefaultXYZDataset();
		for (XYSeriesParamsDouble serie : params.getSeries()) {
			dataset.addSeries(serie.getNomeSerie(), serie.getDados());
		}

		JFreeChart chart = ChartFactory.createBubbleChart(params.getTitulo(),
				params.getRotuloXAxis(), params.getRotuloYAxis(), dataset,
				(params.isOrientacaoPlotVertical() ? PlotOrientation.VERTICAL
						: PlotOrientation.HORIZONTAL), params
						.isMostrarLegenda(), params.isUsarTooltips(), params
						.isGerarURLs());

		return chart;
	}

	public JFreeChart criarHistogramChart(HistogramChartParams params) {
		DefaultIntervalXYDataset dataset = new DefaultIntervalXYDataset();
		for (XYSeriesParamsDouble serie : params.getSeries()) {
			dataset.addSeries(serie.getNomeSerie(), serie.getDados());
		}
		JFreeChart chart = ChartFactory.createHistogram(params.getTitulo(),
				params.getRotuloXAxis(), params.getRotuloYAxis(), dataset,
				(params.isOrientacaoPlotVertical() ? PlotOrientation.VERTICAL
						: PlotOrientation.HORIZONTAL), params
						.isMostrarLegenda(), params.isUsarTooltips(), params
						.isGerarURLs());

		return chart;
	}

	public JFreeChart criarXYBarChart(XYBarChartParams params) {
		DefaultIntervalXYDataset dataset = new DefaultIntervalXYDataset();
		for (XYSeriesParamsDouble serie : params.getSeries()) {
			dataset.addSeries(serie.getNomeSerie(), serie.getDados());
		}
		JFreeChart chart = ChartFactory.createXYBarChart(params.getTitulo(),
				params.getRotuloXAxis(), params.isMostrarTempoXAxis(), params
						.getRotuloYAxis(), dataset, // Dataset
				(params.isOrientacaoPlotVertical() ? PlotOrientation.VERTICAL
						: PlotOrientation.HORIZONTAL), // Plot Orientation
				params.isMostrarLegenda(), // Show Legend
				params.isUsarTooltips(), // Use tooltips
				params.isGerarURLs() // Configure chart to generate URLs?
				);

		return chart;
	}

	public JFreeChart criarBoxAndWhiskerChart(BoxAndWhiskerChartParams params) {
		DefaultBoxAndWhiskerCategoryDataset dataset = new DefaultBoxAndWhiskerCategoryDataset();
		BoxAndWhiskerItem bwItem;
		for (BoxAndWhiskerChartDados item : params.getDados()) {
			bwItem = new BoxAndWhiskerItem(item.getMedia(), item.getMediana(),
					item.getQ1(), item.getQ3(), item.getMinValorRegular(),
					item.getMaxValorRegular(), item.getMinAnexo(),
					item.getMaxAnexo(), item.getAnexos());
			dataset.add(bwItem, params.getChaveLinha(), params.getChaveColuna());
		}
		// (title, categoryAxisLabel, valueAxisLabel, dataset, legend)
		// (title, timeAxisLabel, valueAxisLabel, dataset, legend)

		JFreeChart chart = ChartFactory
				.createBoxAndWhiskerChart(params.getTitulo(),
						params.getRotuloCategoriaAxis(),
						params.getRotuloValorAxis(), dataset,
						params.isMostrarLegenda());

		return chart;
	}

	public JFreeChart criarStackedAreaChart(StackedAreaChartParams params) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for (CategoryDados dado : params.getDados()) {
			dataset.setValue(dado.getValor(), dado.getChaveLinha(),
					dado.getChaveColuna());
		}
		JFreeChart chart = ChartFactory.createStackedAreaChart(params
				.getTitulo(), params.getRotuloCategoriaAxis(), params
				.getRotuloValorAxis(), dataset, (params
				.isOrientacaoPlotVertical() ? PlotOrientation.VERTICAL
				: PlotOrientation.HORIZONTAL), params.isMostrarLegenda(),
				params.isUsarTooltips(), params.isGerarURLs());

		return chart;
	}

	public JFreeChart criarScatterChart(ScatterChartParams params) {
		XYSeries series;
		XYSeriesCollection dataset = new XYSeriesCollection();
		for (XYSeriesParams serie : params.getSeries()) {
			series = new XYSeries(serie.getNomeSerie());
			for (XYDados dado : serie.getDados()) {
				series.add(dado.getSerieX(), dado.getSerieY());
			}
			dataset.addSeries(series);
		}
		JFreeChart chart = ChartFactory.createScatterPlot(params.getTitulo(),
				params.getRotuloXAxis(), params.getRotuloYAxis(), dataset,
				(params.isOrientacaoPlotVertical() ? PlotOrientation.VERTICAL
						: PlotOrientation.HORIZONTAL), params
						.isMostrarLegenda(), params.isUsarTooltips(), params
						.isGerarURLs());

		return chart;
	}

	public JFreeChart criarHighLowChart(HighLowChartParams params) {
		OHLCSeries pop;
		OHLCSeriesCollection dataset = new OHLCSeriesCollection();
		for (OHLCSeriesParams serie : params.getSeries()) {
			pop = new OHLCSeries(serie.getNomeSerie());
			for (OHLCSeriesDados dado : serie.getDados()) {
				pop.add(new Day(dado.getDia(), dado.getMes(), dado.getAno()),
						dado.getValor().doubleValue(), dado.getValor2(),
						dado.getValor3(), dado.getValor4());
			}
			dataset.addSeries(pop);
		}

		JFreeChart chart = ChartFactory.createHighLowChart(params.getTitulo(),
				params.getRotuloTempoAxis(), params.getRotuloValorAxis(),
				dataset, params.isMostrarLegenda());

		XYPlot plot = chart.getXYPlot();
		DateAxis axis = (DateAxis) plot.getDomainAxis();
		axis.setDateFormatOverride(new SimpleDateFormat("dd/MM/yyyy"));

		return chart;
	}

	public JFreeChart criarCandlestickChart(CandlestickChartParams params) {
		OHLCSeries pop;
		OHLCSeriesCollection dataset = new OHLCSeriesCollection();
		for (OHLCSeriesParams serie : params.getSeries()) {
			pop = new OHLCSeries(serie.getNomeSerie());
			for (OHLCSeriesDados dado : serie.getDados()) {
				pop.add(new Day(dado.getDia(), dado.getMes(), dado.getAno()),
						dado.getValor().doubleValue(), dado.getValor2(),
						dado.getValor3(), dado.getValor4());
			}
			dataset.addSeries(pop);
		}

		JFreeChart chart = ChartFactory
				.createCandlestickChart(params.getTitulo(),
						params.getRotuloTempoAxis(),
						params.getRotuloValorAxis(), dataset,
						params.isMostrarLegenda());

		XYPlot plot = chart.getXYPlot();
		DateAxis axis = (DateAxis) plot.getDomainAxis();
		axis.setDateFormatOverride(new SimpleDateFormat("dd/MM/yyyy"));

		return chart;
	}

	public JFreeChart criarGanttChart(GanttChartParams params) {
		String[] nomeSeries = new String[params.getDados().length];
		Number[][] partidas = new Number[params.getDados().length][];
		Number[][] chegadas = new Number[params.getDados().length][];
		GanttChartDados item;
		for (int i = 0; i < params.getDados().length; i++) {
			item = params.getDados()[i];
			nomeSeries[i] = item.getNomeSerie();

			partidas[i] = new Number[item.getPartidas().length];
			for (int j = 0; j < item.getPartidas().length; j++) {
				partidas[i][j] = item.getPartidas()[j];
			}

			chegadas[i] = new Number[item.getChegadas().length];
			for (int j = 0; j < item.getChegadas().length; j++) {
				chegadas[i][j] = item.getChegadas()[j];
			}
		}
		DefaultIntervalCategoryDataset dataset = new DefaultIntervalCategoryDataset(
				nomeSeries, partidas, chegadas);

		JFreeChart chart = ChartFactory.createGanttChart(params.getTitulo(),
				params.getRotuloCategoriaAxis(), params.getRotuloDataAxis(),
				dataset, params.isMostrarLegenda(), params.isUsarTooltips(),
				params.isGerarURLs());

		return chart;
	}

}
