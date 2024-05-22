package test;

import java.util.List;
import java.util.Map;

import util.JSON;
import util.PDF;
import util.CSV;

public class GuardarArchivosTest {

	public static void main(String[] args) {

		String pdfFilePath = "E:/Desktop/resumen.pdf";
		String csvFilePath = "E:/Desktop/archivo.csv";
		String jsonFilePath = "E:/Desktop/json.json";

		try {
			// Leer el PDF
			String pdfText = PDF.read(pdfFilePath);

			// Parsear el texto del PDF
			List<Map<String, String>> transactions = PDF.parseTransactions(pdfText);
			Map<String, String> generalInfo = PDF.parseGeneralInfo(pdfText);

			// Escribir CSV
			CSV.writeCSV(csvFilePath, transactions, generalInfo);

			// Escribir JSON
			JSON.writeJSON(jsonFilePath, transactions, generalInfo);

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("Archivos guardados");

	}

}
