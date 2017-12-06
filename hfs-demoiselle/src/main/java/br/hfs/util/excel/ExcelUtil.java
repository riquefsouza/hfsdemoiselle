package br.hfs.util.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import br.hfs.util.correio.Correio;

public class ExcelUtil implements Serializable {
	private static final long serialVersionUID = 1L;

	private Logger log = Logger.getLogger("ExcelUtil");

	public List<Correio> lerArquivoExcelXLSX(String arquivo) throws ExcelException {
		List<Correio> lista = new ArrayList<Correio>();
		try {
			XSSFWorkbook wb = new XSSFWorkbook(new File(arquivo));
			XSSFSheet sheet = wb.getSheetAt(0);
			//XSSFCell celula;
			Correio co;
			for (int nlinha = 1; nlinha < sheet.getLastRowNum(); nlinha++) {
				XSSFRow header = sheet.getRow(nlinha);
				
				co = fromExcel((long)nlinha, header);
				lista.add(co);

				/*
				for (int i = 0; i < header.getPhysicalNumberOfCells(); i++) {
					celula = header.getCell(i);
					lista.add(celula);
				}
				*/
			}

			wb.close();
		} catch (IOException e) {
			throw new ExcelException(log, e.getMessage());
		} catch (InvalidFormatException e) {
			throw new ExcelException(log, e.getMessage());
		}
		
		return lista;
	}
	
	public List<Correio> lerArquivoExcelXLS(String arquivo) throws ExcelException {
		List<Correio> lista = new ArrayList<Correio>();
		try {
			FileInputStream fis = FileUtils.openInputStream(new File(arquivo));

			HSSFWorkbook wb = new HSSFWorkbook(fis);
			HSSFSheet sheet = wb.getSheetAt(0);
			//HSSFCell celula;
			Correio co;
			for (int nlinha = 1; nlinha < sheet.getLastRowNum(); nlinha++) {
				HSSFRow header = sheet.getRow(nlinha);

				co = fromExcel((long)nlinha, header);
				lista.add(co);
				/*
				for (int i = 0; i < header.getPhysicalNumberOfCells(); i++) {
					celula = header.getCell(i);
					lista.add(celula);
				}
				*/
			}

			wb.close();
		} catch (IOException e) {
			throw new ExcelException(log, e.getMessage());
		}
		return lista;
	}

	public Correio fromExcel(Long id, XSSFRow linha){
		Correio co = new Correio();
		co.setId(id);
		co.setUf(linha.getCell(0).getStringCellValue());
		co.setEstado(linha.getCell(1).getStringCellValue());
		co.setUfCep1(Double.toString(linha.getCell(2).getNumericCellValue()));
		co.setUfCep2(Double.toString(linha.getCell(3).getNumericCellValue()));
		co.setCidade(linha.getCell(4).getStringCellValue());
		co.setLogradouro(linha.getCell(5).getStringCellValue());
		co.setBairro(linha.getCell(6).getStringCellValue());
		co.setCep(linha.getCell(7).getStringCellValue());
		co.setTipoLogradouro(linha.getCell(8).getStringCellValue());
		return co;	
	}
	
	public Correio fromExcel(Long id, HSSFRow linha){
		Correio co = new Correio();
		co.setId(id);
		co.setUf(linha.getCell(0).getStringCellValue());
		co.setEstado(linha.getCell(1).getStringCellValue());
		co.setUfCep1(Double.toString(linha.getCell(2).getNumericCellValue()));
		co.setUfCep2(Double.toString(linha.getCell(3).getNumericCellValue()));
		co.setCidade(linha.getCell(4).getStringCellValue());
		co.setLogradouro(linha.getCell(5).getStringCellValue());
		co.setBairro(linha.getCell(6).getStringCellValue());
		co.setCep(linha.getCell(7).getStringCellValue());
		co.setTipoLogradouro(linha.getCell(8).getStringCellValue());
		return co;
	}
	
}
