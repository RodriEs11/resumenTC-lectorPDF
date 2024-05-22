package datosJson;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DatosJson {

	@JsonProperty("generalInfo")
	private Object informacion;

	@JsonProperty("transactions")
	private List<ConsumoJson> consumos;


}
