package test;

import java.util.Set;

import objetos.Consumo;
import util.JSON;

public class ParsearJsonTest {

	public static void main(String[] args) {

		String jsonPath = "E:/Desktop/json.json";
		Set<Consumo> consumos = JSON.jsonToListConsumo(jsonPath);

		System.out.println(consumos.size());

		double total = 0;
		for (Consumo consumo : consumos) {
			total += consumo.getPesos();
			System.out.println(consumo);
		}

		System.out.println(total);
	}

}
