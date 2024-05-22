package util;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import datosJson.ConsumoJson;
import datosJson.DatosJson;
import objetos.Consumo;

public class JSON {

	public static Set<Consumo> jsonToListConsumo(String jsonPath) {

		Set<Consumo> consumos = new HashSet<Consumo>();

		try {
			ObjectMapper objectMapper = new ObjectMapper();
			String json = read(jsonPath);

			// Parsear el JSON a un objeto Datos
			DatosJson listaJson = objectMapper.readValue(json, DatosJson.class);

			for (ConsumoJson consumo : listaJson.getConsumos()) {

				consumos.add(new Consumo(consumo.getFecha(), consumo.getPesos(), consumo.getDetalle(),
						consumo.getComprobante(), consumo.getCuotaActual(), consumo.getTotalCuotas()));

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return consumos;

	}

	public static String read(String file) throws IOException {

		return new String(Files.readAllBytes(Paths.get(file)), StandardCharsets.UTF_8);
	}

	public static void writeJSON(String filePath, List<Map<String, String>> transactions,
			Map<String, String> generalInfo) throws IOException {

		ObjectMapper objectMapper = new ObjectMapper();
		ObjectNode rootNode = objectMapper.createObjectNode();

		// Añadir información general al nodo de datos
		ObjectNode generalInfoNode = objectMapper.createObjectNode();
		generalInfo.forEach(generalInfoNode::put);
		rootNode.set("generalInfo", generalInfoNode);

		// Añadir transacciones al nodo de datos
		ArrayNode transactionArrayNode = objectMapper.createArrayNode();
		for (Map<String, String> transaction : transactions) {
			ObjectNode transactionNode = objectMapper.createObjectNode();
			transaction.forEach(transactionNode::put);
			transactionArrayNode.add(transactionNode);
		}
		rootNode.set("transactions", transactionArrayNode);

		// Escribir el JSON en el archivo
		objectMapper.writeValue(new File(filePath), rootNode);
	}
}
