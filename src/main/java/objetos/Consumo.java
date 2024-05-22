package objetos;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import lombok.Getter;


@Getter
public class Consumo {

	private LocalDate fecha;

	private double pesos;

	private String detalle;

	private int comprobante;

	private int cuotaActual;

	private int totalCuotas;

	public Consumo(String fecha, String pesos, String detalle, String comprobante, String cuotaActual,
			String totalCuotas) {
		super();
		this.fecha = setFecha(fecha);
		this.pesos = setPesos(pesos);
		this.detalle = setDetalle(detalle);
		this.comprobante = setComprobante(comprobante);
		this.cuotaActual = setCuotaActual(cuotaActual);
		this.totalCuotas = setTotalCuotas(totalCuotas);
	}

	private int setTotalCuotas(String totalCuotas) {

		return Integer.parseInt(totalCuotas);
	}

	private int setCuotaActual(String cuotaActual) {
		return Integer.parseInt(cuotaActual);
	}

	private int setComprobante(String comprobante) {

		// Formato del comprobante en el json {"Comprobante": "414169*"}
		String cadenaSinAsterisco = comprobante.replace("*", "");
		int comprobanteInt = Integer.parseInt(cadenaSinAsterisco);

		return comprobanteInt;
	}

	private String setDetalle(String detalle) {

		return detalle;
	}

	private double setPesos(String pesos) {

		String pesosTemp = "";
		boolean esNegativo = false;


		// Si es negativo, llega con el formato como 14.000,00-
		// Se elimina el guion
		if (pesos.endsWith("-")) {
			pesos = pesos.substring(0, pesos.length() - 1);
			esNegativo = true;
		}

		// Reemplazar la coma por un punto para tener un formato decimal válido
		// "6.232,96" --> "6.232.96"
		pesosTemp = pesos.replace(".", "").replace(",", ".");
		
		double numero = esNegativo ? Double.parseDouble(pesosTemp) * - 1 : Double.parseDouble(pesosTemp);

		return numero;
	}

	private LocalDate setFecha(String fechaString) {

		// Formato de la fecha en el json {"Fecha": "04.12.23"}
		String formatoFecha = "dd.MM.yy";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatoFecha);
		LocalDate fecha = LocalDate.parse(fechaString, formatter);

		return fecha;
	}

	public String toString() {
		return String.format("%s [$%.2f] [%s] [%d] %d/%d", this.fecha, this.pesos, this.detalle, this.comprobante,
				this.cuotaActual, this.totalCuotas);
	}

}
