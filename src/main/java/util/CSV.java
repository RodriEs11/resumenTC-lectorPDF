package util;

import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.opencsv.CSVWriter;

import objetos.Consumo;

public class CSV {

	public static void writeCSV(String filePath, List<Map<String, String>> data, Map<String, String> generalInfo)
			throws IOException {
		CSVWriter writer = new CSVWriter(new FileWriter(filePath));

		// Write general info
		for (Map.Entry<String, String> entry : generalInfo.entrySet()) {
			writer.writeNext(new String[] { entry.getKey(), entry.getValue() });
		}

		// Empty line to separate sections
		writer.writeNext(new String[] { "" });

		// Write header for transactions
		String[] header = { "Fecha", "Comprobante", "Detalle", "CuotaActual", "TotalCuotas", "Pesos" };
		writer.writeNext(header);

		// Write transactions
		for (Map<String, String> transaction : data) {
			String[] row = { transaction.get("Fecha"), transaction.get("Comprobante"), transaction.get("Detalle"),
					transaction.get("CuotaActual"), transaction.get("TotalCuotas"), transaction.get("Pesos") };
			writer.writeNext(row);
		}

		writer.close();
	}

	public static Consumo parseLineaCSV(String lineaCSV) throws ParseException {
		String[] partes = lineaCSV.split(",");

		String fecha = partes[0];
		String comprobante = partes[1].replaceAll("\"", "");
		String detalle = partes[2].replaceAll("\"", "");
		String cuotaActual = partes[3];
		String totalCuotas = partes[4];
		String pesos = partes[5];

		return new Consumo(fecha, comprobante, detalle, cuotaActual, totalCuotas, pesos);
	}

}
