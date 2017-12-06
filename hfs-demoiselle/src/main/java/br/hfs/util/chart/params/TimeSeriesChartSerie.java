package br.hfs.util.chart.params;

import java.util.List;

public class TimeSeriesChartSerie extends SeriesParams {
	private static final long serialVersionUID = 1L;
	
	public TimeSeriesChartSerie(){
		super();
	}
	
	public TimeSeriesChartSerie(String nomeSerie,
			List<SeriesDados> dados) {
		super(nomeSerie, dados);
	}
}