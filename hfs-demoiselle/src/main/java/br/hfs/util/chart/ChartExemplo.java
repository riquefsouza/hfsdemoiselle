package br.hfs.util.chart;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;

public class ChartExemplo {

	public static void main(String[] args) {
		try {
			ChartUtil util = new ChartUtil();
			ChartUtilExemplos cue = new ChartUtilExemplos();
			
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			
			JFreeChart chart = cue.PieChartExemplo(util, false);
			ChartUtilities.writeChartAsJPEG(out, chart, 500, 300);
			
			File arq = new File("c:\\VM\\saida.jpg");
			FileUtils.writeByteArrayToFile(arq, out.toByteArray());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
