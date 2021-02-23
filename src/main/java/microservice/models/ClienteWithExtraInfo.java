package microservice.models;

import java.time.LocalDate;

public class ClienteWithExtraInfo extends Cliente {
	private LocalDate fechaMuerte;

	public LocalDate getFechaMuerte() {
		return fechaMuerte;
	}

	public void setFechaMuerte(LocalDate fechaMuerte) {
		this.fechaMuerte = fechaMuerte;
	}

}
