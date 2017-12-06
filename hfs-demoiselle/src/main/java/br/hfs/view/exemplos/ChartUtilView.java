package br.hfs.view.exemplos;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.inject.Inject;

import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import br.gov.frameworkdemoiselle.message.MessageContext;
import br.hfs.util.chart.ChartUtil;
import br.hfs.util.chart.ChartUtilExemplos;

@ManagedBean
public class ChartUtilView implements Serializable {
	private static final long serialVersionUID = 1L;

	// private StreamedContent grafico;

	@Inject
	ChartUtil util;

	@Inject
	ChartUtilExemplos cue;

	@Inject
	MessageContext messageContext;

	private StreamedContent gerarGrafico(JFreeChart chart) {
		StreamedContent grafico = null;
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ChartUtilities.writeChartAsJPEG(out, chart, 500, 300);
			grafico = new DefaultStreamedContent(new ByteArrayInputStream(
					out.toByteArray()), "image/jpeg");
		} catch (IOException e) {
			messageContext.add(e.getMessage());
		}

		return grafico;
	}

	public StreamedContent getPieChart() {
		return gerarGrafico(cue.PieChartExemplo(util, false));
	}

	public StreamedContent getPieChart3D() {
		return gerarGrafico(cue.PieChartExemplo(util, true));
	}

	public StreamedContent getXYLineChart() {
		return gerarGrafico(cue.XYLineChartExample(util));
	}

	public StreamedContent getTimeSeries() {
		return gerarGrafico(cue.TimeSeriesExample(util));
	}

	public StreamedContent getBarChart() {
		return gerarGrafico(cue.BarChartExample(util, false));
	}

	public StreamedContent getBarChart3D() {
		return gerarGrafico(cue.BarChartExample(util, true));
	}

	public StreamedContent getAreaChart() {
		return gerarGrafico(cue.AreaChartExample(util));
	}

	public StreamedContent getXYAreaChart() {
		return gerarGrafico(cue.XYAreaChartExample(util));
	}

	public StreamedContent getStackedBarChart() {
		return gerarGrafico(cue.StackedBarChartExample(util, false));
	}

	public StreamedContent getStackedBarChart3D() {
		return gerarGrafico(cue.StackedBarChartExample(util, true));
	}

	public StreamedContent getXYStepChart() {
		return gerarGrafico(cue.XYStepChartExample(util));
	}

	public StreamedContent getXYStepAreaChart() {
		return gerarGrafico(cue.XYStepAreaChartExample(util));
	}

	public StreamedContent getLineChart() {
		return gerarGrafico(cue.LineChartExample(util, false));
	}

	public StreamedContent getLineChart3D() {
		return gerarGrafico(cue.LineChartExample(util, true));
	}

	public StreamedContent getBubbleChart() {
		return gerarGrafico(cue.BubbleChartExample(util));
	}

	public StreamedContent getHistogramChart() {
		return gerarGrafico(cue.HistogramChartExample(util));
	}

	public StreamedContent getXYBarChart() {
		return gerarGrafico(cue.XYBarChartExample(util, false));
	}

	public StreamedContent getXYBarChart3D() {
		return gerarGrafico(cue.XYBarChartExample(util, true));
	}

	public StreamedContent getBoxAndWhiskerChart() {
		return gerarGrafico(cue.BoxAndWhiskerChartExample(util));
	}

	public StreamedContent getStackedAreaChart() {
		return gerarGrafico(cue.StackedAreaChartExample(util));
	}

	public StreamedContent getScatterChart() {
		return gerarGrafico(cue.ScatterChartExample(util));
	}

	public StreamedContent getHighLowChart() {
		return gerarGrafico(cue.HighLowChartExample(util));
	}

	public StreamedContent getCandlestickChart() {
		return gerarGrafico(cue.CandlestickChartExample(util));
	}

	public StreamedContent getGanttChart() {
		return gerarGrafico(cue.GanttChartExample(util));
	}
}
