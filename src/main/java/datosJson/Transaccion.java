package datosJson;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
public class Transaccion {
	
	@JsonProperty("transactions")
	private List<ConsumoJson> consumos;

}
