package br.hfs.util.feriado;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;

public class FeriadoUtil implements Serializable {
	private static final long serialVersionUID = 1L;

	private int diaDomingoPascoa;
	private int mesDomingoPascoa;
	private int diaSegundaCarnaval;
	private int mesSegundaCarnaval;
	private int diaTercaCarnaval;
	private int mesTercaCarnaval;
	private int diaQuartaCinzas;
	private int mesQuartaCinzas;
	private int diaQuintaCorpusChristi;
	private int mesQuintaCorpusChristi;
	private int diaSextaPaixaoCristo;
	private int mesSextaPaixaoCristo;
	private int ano;

	/*
	 * obtido do site http://www.ghiorzi.org/portug2.htm
	 * 
	 * 
	 * PÁSCOA - CARNAVAL - CORPUS CHRISTI
	 * 
	 * Todos os feriados eclesiásticos são calculados em função da Páscoa e esta
	 * é calculada em função da Lua Cheia. Veja as explicações a seguir:
	 * 
	 * A PÁSCOA ocorre no primeiro domingo após a primeira lua cheia que se
	 * verificar a partir de 21 de março. A Sexta-Feira da Paixão é a que
	 * antecede o Domingo de Páscoa. A terça-feira de CARNAVAL ocorre 47 dias
	 * antes da Páscoa e a quinta-feira do CORPUS CHRISTI ocorre 60 dias após a
	 * Páscoa. Domingo de RAMOS é o que antecede o domingo da Páscoa, a QUARESMA
	 * são os 40 dias entre o Carnaval e o domingo de Ramos, a quinta-feira da
	 * ASCENSÃO ocorre 39 dias após a Páscoa e o domingo de PENTECOSTES vem 10
	 * dias depois da Ascensão.
	 */
	public void calcularFeriadoMovel(int calYear) throws Exception {
		double calA, calB, calC, calD, calE, calF, calG, calH, calI, calK, calL, calM;
		double calP, calQ, calR, calS, calT, calU, calV, calW, calX, calY, calZ;
		double julDia, julMes, julAno, julAnoAj, julMesAj, julA, julB, julC, julD;
		double julE, julJul, julCar, julCor, julJul2, julD2, julE2;
		double julF2, julG2, julH2, julI2, julJ2, julK2;
		double calDiaCar, calMesCar, calDiaCor, calMesCor;
		// String scalMesCar, scalMesCor, scalY, scalZ;

		ano = calYear;

		calZ = 0;
		calY = 0;
		julA = 0;
		julB = 0;
		julC = 0;
		julE2 = 0;
		julD2 = 0;
		julK2 = 0;

		if (calYear < 1) {
			throw new Exception(
					"O Ano deve ser um número inteiro, positivo, de 1 ao infinito.");
		}
		if (calYear - Math.round(calYear - .5) != 0) {
			throw new Exception(
					"O Ano deve ser um número inteiro, positivo, de 1 ao infinito.");
		}

		calA = calYear % 19;
		calB = Math.round(calYear / 100 - .5);
		calC = calYear % 100;
		calD = Math.round(calB / 4 - .5);
		calE = calB % 4;

		calF = Math.round((calB + 8) / 25 - .5);
		calG = Math.round((calB - calF + 1) / 3 - .5);
		calH = (19 * calA + calB - calD - calG + 15) % 30;
		calI = Math.round(calC / 4 - .5);
		calK = calC % 4;
		calL = (32 + 2 * calE + 2 * calI - calH - calK) % 7;

		calM = Math.round((calA + 11 * calH + 22 * calL) / 451 - .5);
		calP = Math.round((calH + calL - 7 * calM + 114) / 31 - .5);
		calQ = (calH + calL - 7 * calM + 114) % 31;
		calR = calYear % 4;
		calS = calYear % 7;
		calT = calYear % 19;

		calU = (19 * calT + 15) % 30;
		calV = (2 * calR + 4 * calS - calU + 34) % 7;
		calW = Math.round((calU + calV + 114) / 31 - .5);
		calX = (calU + calV + 114) % 31;

		if (calYear < 1583) {
			calY = calW;
		}
		if (calYear < 1583) {
			calZ = calX + 1;
		}
		if (calYear >= 1583) {
			calY = calP;
		}
		if (calYear >= 1583) {
			calZ = calQ + 1;
		}

		julDia = calZ;
		julMes = calY;
		julAno = calYear;

		if (julMes < 3) {
			julAnoAj = julAno - 1;
		} else {
			julAnoAj = julAno;
		}
		if (julMes < 3) {
			julMesAj = julMes + 12;
		} else {
			julMesAj = julMes;
		}

		if ((julAno > 1582) || (julMes > 10) && (julAno == 1582)
				|| (julDia >= 15) && (julMes == 10) && (julAno == 1582)) {
			julA = (julAnoAj / 100) - (julAnoAj / 100) % 1;
		}

		if ((julAno > 1582) || (julMes > 10) && (julAno == 1582)
				|| (julDia >= 15) && (julMes == 10) && (julAno == 1582)) {
			julB = (julA / 4) - (julA / 4) % 1;
		}

		if ((julAno > 1582) || (julMes > 10) && (julAno == 1582)
				|| (julDia >= 15) && (julMes == 10) && (julAno == 1582)) {
			julC = 2 - julA + julB;
		}

		if ((julAno < 1582) || (julMes < 10) && (julAno == 1582)
				|| (julDia <= 4) && (julMes == 10) && (julAno == 1582)) {
			julC = 0;
		}

		julD = (365.25 * (julAnoAj + 4716)) - (365.25 * (julAnoAj + 4716)) % 1;
		julE = (30.6001 * (julMesAj + 1)) - (30.6001 * (julMesAj + 1)) % 1;
		julJul = julD + julE + julDia + .5 + julC - 1524.5;

		julCar = julJul - 47;
		julCor = julJul + 60;

		julJul2 = julCar;

		if (julJul2 < 2299161) {
			julD2 = julJul2;
		}
		if (julJul2 > 2299160) {
			julE2 = ((julJul2 - 1867216.25) / 36524.25)
					- ((julJul2 - 1867216.25) / 36524.25) % 1;
		}
		if (julJul2 > 2299160) {
			julD2 = julJul2 + 1 + julE2 - ((julE2 / 4) - (julE2 / 4) % 1);
		}
		julF2 = julD2 + 1524;
		julG2 = ((julF2 - 122.1) / 365.25) - ((julF2 - 122.1) / 365.25) % 1;
		julH2 = (julG2 * 365.25) - (julG2 * 365.25) % 1;
		julI2 = ((julF2 - julH2) / 30.6001) - ((julF2 - julH2) / 30.6001) % 1;
		julJ2 = julF2 - julH2 - ((julI2 * 30.6001) - (julI2 * 30.6001) % 1);
		if (julI2 < 14) {
			julK2 = julI2 - 1;
		}
		if (julI2 > 13) {
			julK2 = julI2 - 13;
		}

		calDiaCar = julJ2;
		calMesCar = julK2;

		julJul2 = julCor;

		if (julJul2 < 2299161) {
			julD2 = julJul2;
		}
		if (julJul2 > 2299160) {
			julE2 = ((julJul2 - 1867216.25) / 36524.25)
					- ((julJul2 - 1867216.25) / 36524.25) % 1;
		}
		if (julJul2 > 2299160) {
			julD2 = julJul2 + 1 + julE2 - ((julE2 / 4) - (julE2 / 4) % 1);
		}
		julF2 = julD2 + 1524;
		julG2 = ((julF2 - 122.1) / 365.25) - ((julF2 - 122.1) / 365.25) % 1;
		julH2 = (julG2 * 365.25) - (julG2 * 365.25) % 1;
		julI2 = ((julF2 - julH2) / 30.6001) - ((julF2 - julH2) / 30.6001) % 1;
		julJ2 = julF2 - julH2 - ((julI2 * 30.6001) - (julI2 * 30.6001) % 1);
		if (julI2 < 14) {
			julK2 = julI2 - 1;
		}
		if (julI2 > 13) {
			julK2 = julI2 - 13;
		}

		calDiaCor = julJ2;
		calMesCor = julK2;

		diaDomingoPascoa = (int) calZ;
		mesDomingoPascoa = (int) calY;
		diaTercaCarnaval = (int) calDiaCar;
		mesTercaCarnaval = (int) calMesCar;
		diaQuintaCorpusChristi = (int) calDiaCor;
		mesQuintaCorpusChristi = (int) calMesCor;

		// Sexta-Feira da Paixão é a que antecede o Domingo de Páscoa
		calcularSextaPaixaoCristo(diaDomingoPascoa, mesDomingoPascoa, calYear);

		// Segunda-Feira de Carnaval e Quarta-Feira de Cinzas
		calcularSegundaCarnavalQuartaCinzas(diaTercaCarnaval, mesTercaCarnaval,
				calYear);
	}

	private void calcularSegundaCarnavalQuartaCinzas(int diaCarnaval,
			int mesCarnaval, int anoCarnaval) {
		Calendar data1 = Calendar.getInstance();
		data1.set(anoCarnaval, mesCarnaval - 1, diaCarnaval);
		data1.add(Calendar.DATE, -1);

		// segunda
		diaSegundaCarnaval = data1.get(Calendar.DATE);
		mesSegundaCarnaval = data1.get(Calendar.MONTH) + 1;

		data1.add(Calendar.DATE, 2);
		// quarta
		diaQuartaCinzas = data1.get(Calendar.DATE);
		mesQuartaCinzas = data1.get(Calendar.MONTH) + 1;
	}

	private void calcularSextaPaixaoCristo(int diaPascoa, int mesPascoa,
			int anoPascoa) {
		Calendar data1 = Calendar.getInstance();
		data1.set(anoPascoa, mesPascoa - 1, diaPascoa);
		data1.add(Calendar.DATE, -2);

		// sexta
		if (data1.get(Calendar.DAY_OF_WEEK) == 6) {
			diaSextaPaixaoCristo = data1.get(Calendar.DATE);
			mesSextaPaixaoCristo = data1.get(Calendar.MONTH) + 1;
		}
	}

	public double getDiaDomingoPascoa() {
		return diaDomingoPascoa;
	}

	public double getMesDomingoPascoa() {
		return mesDomingoPascoa;
	}

	public double getDiaTercaCarnaval() {
		return diaTercaCarnaval;
	}

	public double getMesTercaCarnaval() {
		return mesTercaCarnaval;
	}

	public double getDiaQuintaCorpusChristi() {
		return diaQuintaCorpusChristi;
	}

	public double getMesQuintaCorpusChristi() {
		return mesQuintaCorpusChristi;
	}

	public double getDiaSextaPaixaoCristo() {
		return diaSextaPaixaoCristo;
	}

	public double getMesSextaPaixaoCristo() {
		return mesSextaPaixaoCristo;
	}

	public int getDiaSegundaCarnaval() {
		return diaSegundaCarnaval;
	}

	public int getMesSegundaCarnaval() {
		return mesSegundaCarnaval;
	}

	public int getDiaQuartaCinzas() {
		return diaQuartaCinzas;
	}

	public int getMesQuartaCinzas() {
		return mesQuartaCinzas;
	}

	public int getAno() {
		return ano;
	}

	public String getDomingoPascoaTexto() {
		return (diaDomingoPascoa <= 9 ? "0" + diaDomingoPascoa
				: diaDomingoPascoa)
				+ "/"
				+ (mesDomingoPascoa <= 9 ? "0" + mesDomingoPascoa
						: mesDomingoPascoa) + "/" + ano;
	}

	public Date getDomingoPascoaData() {
		Calendar cal = Calendar.getInstance();
		cal.set(ano, mesDomingoPascoa - 1, diaDomingoPascoa);
		return cal.getTime();
	}

	public String getSegundaCarnavalTexto() {
		return (diaSegundaCarnaval <= 9 ? "0" + diaSegundaCarnaval
				: diaSegundaCarnaval)
				+ "/"
				+ (mesSegundaCarnaval <= 9 ? "0" + mesSegundaCarnaval
						: mesSegundaCarnaval) + "/" + ano;
	}

	public Date getSegundaCarnavalData() {
		Calendar cal = Calendar.getInstance();
		cal.set(ano, mesSegundaCarnaval - 1, diaSegundaCarnaval);
		return cal.getTime();
	}

	public String getTercaCarnavalTexto() {
		return (diaTercaCarnaval <= 9 ? "0" + diaTercaCarnaval
				: diaTercaCarnaval)
				+ "/"
				+ (mesTercaCarnaval <= 9 ? "0" + mesTercaCarnaval
						: mesTercaCarnaval) + "/" + ano;
	}

	public Date getTercaCarnavalData() {
		Calendar cal = Calendar.getInstance();
		cal.set(ano, mesTercaCarnaval - 1, diaTercaCarnaval);
		return cal.getTime();
	}

	public String getQuartaCinzasTexto() {
		return (diaQuartaCinzas <= 9 ? "0" + diaQuartaCinzas : diaQuartaCinzas)
				+ "/"
				+ (mesQuartaCinzas <= 9 ? "0" + mesQuartaCinzas
						: mesQuartaCinzas) + "/" + ano;
	}

	public Date getQuartaCinzasData() {
		Calendar cal = Calendar.getInstance();
		cal.set(ano, mesQuartaCinzas - 1, diaQuartaCinzas);
		return cal.getTime();
	}

	public String getQuintaCorpusChristiTexto() {
		return (diaQuintaCorpusChristi <= 9 ? "0" + diaQuintaCorpusChristi
				: diaQuintaCorpusChristi)
				+ "/"
				+ (mesQuintaCorpusChristi <= 9 ? "0" + mesQuintaCorpusChristi
						: mesQuintaCorpusChristi) + "/" + ano;
	}

	public Date getQuintaCorpusChristiData() {
		Calendar cal = Calendar.getInstance();
		cal.set(ano, mesQuintaCorpusChristi - 1, diaQuintaCorpusChristi);
		return cal.getTime();
	}

	public String getSextaPaixaoCristoTexto() {
		return (diaSextaPaixaoCristo <= 9 ? "0" + diaSextaPaixaoCristo
				: diaSextaPaixaoCristo)
				+ "/"
				+ (mesSextaPaixaoCristo <= 9 ? "0" + mesSextaPaixaoCristo
						: mesSextaPaixaoCristo) + "/" + ano;
	}

	public Date getSextaPaixaoCristoData() {
		Calendar cal = Calendar.getInstance();
		cal.set(ano, mesSextaPaixaoCristo - 1, diaSextaPaixaoCristo);
		return cal.getTime();
	}

	public String toString() {
		return "Segunda de Carnaval: " + getSegundaCarnavalTexto() + "\n"
				+ "Terça de Carnaval: " + getTercaCarnavalTexto() + "\n"
				+ "Quarta de Cinzas: " + getQuartaCinzasTexto() + "\n"
				+ "Sexta de Paixão de Cristo: " + getSextaPaixaoCristoTexto()
				+ "\n" + "Domingo de Páscoa: " + getDomingoPascoaTexto() + "\n"
				+ "Quinta de Corpus Christi: " + getQuintaCorpusChristiTexto();
	}

	public ArrayList<Feriado> gerarFeriadosRecife(int anoInicial, int anoFinal)
			throws Exception {
		String fmt = "dd/MM/yyyy";
		ArrayList<Feriado> listaFeriados = new ArrayList<Feriado>();

		for (int ano = anoInicial; ano <= anoFinal; ano++) {
			// Confraternização Universal (Nacional)			
			listaFeriados.add(new Feriado("Confraternização Universal (Nacional)",DateUtils.parseDate("01/01/" + ano, fmt),"01/01/" + ano));

			this.calcularFeriadoMovel(ano);
			// Segunda-feira de Carnaval (Ponto facultativo)
			listaFeriados.add(new Feriado("Segunda-feira de Carnaval (Ponto facultativo)",getSegundaCarnavalData(),this.getSegundaCarnavalTexto()));
			// Terça-feira de Carnaval (Ponto facultativo)
			listaFeriados.add(new Feriado("Terça-feira de Carnaval (Ponto facultativo)", this.getTercaCarnavalData(), this.getTercaCarnavalTexto()));
			// Quarta-feira de Cinzas (Ponto facultativo)
			listaFeriados.add(new Feriado("Quarta-feira de Cinzas (Ponto facultativo)", this.getQuartaCinzasData(),this.getQuartaCinzasTexto()));
			// Paixão de Cristo (Nacional)
			listaFeriados.add(new Feriado("Paixão de Cristo (Nacional)",this.getSextaPaixaoCristoData(),this.getSextaPaixaoCristoTexto()));

			// Tiradentes (Nacional)
			listaFeriados.add(new Feriado("Tiradentes (Nacional)",DateUtils.parseDate("21/04/" + ano, fmt),"21/04/" + ano));
			// Dia Mundial do Trabalho (Nacional)
			listaFeriados.add(new Feriado("Dia Mundial do Trabalho (Nacional)",DateUtils.parseDate("01/05/" + ano, fmt),"01/05/" + ano));
			// São João (Estadual)
			listaFeriados.add(new Feriado("São João (Estadual)",DateUtils.parseDate("24/06/" + ano, fmt),"24/06/" + ano));
			// Dia de Nossa Senhora do Carmo, padroeira do Recife (Municipal -
			// feriado apenas no Recife)
			listaFeriados.add(new Feriado("Dia de Nossa Senhora do Carmo, padroeira do Recife (Municipal - feriado apenas no Recife)",DateUtils.parseDate("16/07/" + ano, fmt),"16/07/" + ano));
			// Independência do Brasil (Nacional)
			listaFeriados.add(new Feriado("Independência do Brasil (Nacional)",DateUtils.parseDate("07/09/" + ano, fmt),"07/09/" + ano));
			// Dia de Nossa Senhora Aparecida (Nacional)
			listaFeriados.add(new Feriado("Dia de Nossa Senhora Aparecida (Nacional)",DateUtils.parseDate("12/10/" + ano, fmt),"12/10/" + ano));
			// Dia do Servidor Público (Ponto facultativo)
			listaFeriados.add(new Feriado("Dia do Servidor Público (Ponto facultativo)",DateUtils.parseDate("28/10/" + ano, fmt),"28/10/" + ano));
			// Finados (Nacional)
			listaFeriados.add(new Feriado("Finados (Nacional)",DateUtils.parseDate("02/11/" + ano, fmt),"02/11/" + ano));
			// Proclamação da República (Nacional)
			listaFeriados.add(new Feriado("Proclamação da República (Nacional)",DateUtils.parseDate("15/11/" + ano, fmt),"15/11/" + ano));
			// Nossa Senhora da Conceição (Municipal - feriado apenas no Recife)
			listaFeriados.add(new Feriado("Nossa Senhora da Conceição (Municipal - feriado apenas no Recife)",DateUtils.parseDate("08/12/" + ano, fmt),"08/12/" + ano));
			// Véspera de Natal (Ponto facultativo após as 12 horas)
			listaFeriados.add(new Feriado("Véspera de Natal (Ponto facultativo após as 12 horas)",DateUtils.parseDate("24/12/" + ano, fmt),"24/12/" + ano));
			// Natal (Nacional)
			listaFeriados.add(new Feriado("Natal (Nacional)",DateUtils.parseDate("25/12/" + ano, fmt),"25/12/" + ano));
			// Véspera de Ano Novo (Ponto facultativo)
			listaFeriados.add(new Feriado("Véspera de Ano Novo (Ponto facultativo)",DateUtils.parseDate("31/12/" + ano, fmt),"31/12/" + ano));
		}

		return listaFeriados;
	}

	private boolean verificaDataNoIntervalo(String data,
			ArrayList<String> listaFeriados) {
		boolean bRetorno = false;

		for (String item : listaFeriados) {
			if (item.equals(data)) {
				bRetorno = true;
				break;
			}
		}

		return bRetorno;
	}

	public int removerFinalDeSemanaFeriados(String dataInicial, int qdeDias,
			ArrayList<String> listaFeriados) throws ParseException {
		Calendar data1 = Calendar.getInstance();
		// SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		data1.setTime(sdf.parse(dataInicial));

		String dataNoIntervalo = "";
		int i = 1;
		while (i <= qdeDias) {

			dataNoIntervalo = sdf.format(data1.getTime());

			if (data1.get(Calendar.DAY_OF_WEEK) == 1
					|| data1.get(Calendar.DAY_OF_WEEK) == 7
					|| verificaDataNoIntervalo(dataNoIntervalo, listaFeriados))
				qdeDias--;

			data1.add(Calendar.DATE, 1);
			i++;
		}

		return qdeDias;
	}
}
