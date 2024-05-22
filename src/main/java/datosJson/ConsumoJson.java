package datosJson;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConsumoJson {

	@JsonProperty("Fecha")
	private String fecha;

	@JsonProperty("Pesos")
	private String pesos;

	@JsonProperty("Detalle")
	private String detalle;

	@JsonProperty("Comprobante")
	private String comprobante;


	@JsonProperty("CuotaActual")
	private String cuotaActual;

	@JsonProperty("TotalCuotas")
	private String totalCuotas;
	
	
	public String toString() {
		return String.format("[%s] [%s] [%s] [%s] [%s] [%s]", this.fecha, this.pesos, this.detalle, this.comprobante, this.cuotaActual, this.totalCuotas);
	}


	
}

