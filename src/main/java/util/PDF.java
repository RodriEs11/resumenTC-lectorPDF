package util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class PDF {

	public static String read(String filePath) throws IOException {
		PDDocument document = PDDocument.load(new File(filePath));
		PDFTextStripper pdfStripper = new PDFTextStripper();
		String text = pdfStripper.getText(document);
		document.close();
		return text;
	}

	public static Map<String, String> parseGeneralInfo(String text) {
		Map<String, String> generalInfo = new HashMap<>();

		// Regex para encontrar las fechas de cierre y vencimiento
		Pattern cierrePattern = Pattern.compile("Cierre actual:\\s+(\\d{2}/\\d{2}/\\d{4})");
		Matcher cierreMatcher = cierrePattern.matcher(text);
		if (cierreMatcher.find()) {
			generalInfo.put("CierreActual", cierreMatcher.group(1));
		}

		Pattern vencimientoPattern = Pattern.compile("Vencimiento:\\s+(\\d{2}/\\d{2}/\\d{4})");
		Matcher vencimientoMatcher = vencimientoPattern.matcher(text);
		if (vencimientoMatcher.find()) {
			generalInfo.put("Vencimiento", vencimientoMatcher.group(1));
		}

		// Regex para saldos y pagos mínimos
		Pattern saldoPattern = Pattern.compile("Saldo\\s+\\$:\\s+([\\d.,]+)");
		Matcher saldoMatcher = saldoPattern.matcher(text);
		if (saldoMatcher.find()) {
			generalInfo.put("SaldoPesos", saldoMatcher.group(1));
		}

		Pattern saldoUSPattern = Pattern.compile("Saldo\\s+U\\$S:\\s+([\\d.,]+)");
		Matcher saldoUSMatcher = saldoUSPattern.matcher(text);
		if (saldoUSMatcher.find()) {
			generalInfo.put("SaldoDolares", saldoUSMatcher.group(1));
		}

		Pattern pagoMinPattern = Pattern.compile("Pago\\s+min\\s+\\$:\\s+([\\d.,]+)");
		Matcher pagoMinMatcher = pagoMinPattern.matcher(text);
		if (pagoMinMatcher.find()) {
			generalInfo.put("PagoMinPesos", pagoMinMatcher.group(1));
		}

		Pattern pagoMinUSPattern = Pattern.compile("Pago\\s+min\\s+U\\$S:\\s+([\\d.,]+)");
		Matcher pagoMinUSMatcher = pagoMinUSPattern.matcher(text);
		if (pagoMinUSMatcher.find()) {
			generalInfo.put("PagoMinDolares", pagoMinUSMatcher.group(1));
		}

		return generalInfo;
	}

	public static List<Map<String, String>> parseTransactions(String text) {
		List<Map<String, String>> transactions = new ArrayList<>();

		// Utilizar regex para encontrar los patrones específicos de los datos que
		// necesitas
		Pattern pattern = Pattern.compile(
				"(\\d{2}\\.\\d{2}\\.\\d{2})\\s+(\\d{6}[\\* ]?)\\s+(.*?)\\s+(Cuota\\s+\\d{2}/\\d{2})?\\s+([\\d.,]+-?)");
		Matcher matcher = pattern.matcher(text);

		while (matcher.find()) {
			Map<String, String> transaction = new HashMap<>();
			transaction.put("Fecha", matcher.group(1));
			transaction.put("Comprobante", matcher.group(2).trim());
			transaction.put("Detalle", matcher.group(3).trim());

			// Verificar si hay datos de cuotas
			String cuotas = matcher.group(4);
			if (cuotas != null) {
				// Dividir la cadena de cuotas "Cuota XX/YY"
				String[] cuotasArray = cuotas.split("\\s+");
				String[] valoresCuotas = cuotasArray[1].split("/");
				transaction.put("CuotaActual", valoresCuotas[0]);
				transaction.put("TotalCuotas", valoresCuotas[1]);
			} else {
				// Establecer valores predeterminados si no hay datos de cuotas
				transaction.put("CuotaActual", "0");
				transaction.put("TotalCuotas", "0");
			}

			String pesos = matcher.group(5);

			transaction.put("Pesos", pesos);

			transactions.add(transaction);
		}

		return transactions;
	}

}
