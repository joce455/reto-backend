package microservice.operations;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import microservice.models.Cliente;
import microservice.models.ClienteWithExtraInfo;

//import microservice.controllers.ClienteWithExtraInfo;

@Component
public class Operations implements IClientOperations {

	@Value("${minimo.esperanza}")
	int minimoesperanzavida;

	@Value("${maximo.esperanza}")
	int maximoesperanzavida;

	@Override
	public double getPromData(List<Cliente> listaClientes) {
		return listaClientes.stream().mapToDouble((x) -> x.getEdad()).summaryStatistics().getAverage();
	}

	@Override
	public double getVarEstandarData(List<Cliente> listaClientes, double promedio) {
		return Math.sqrt((listaClientes.stream().mapToDouble((x) -> Math.pow((x.getEdad() - promedio), 2)).sum())
				/ listaClientes.size());
	}

	@Override
	public List<ClienteWithExtraInfo> getDetailsClients(List<Cliente> listaClientes) {
		return listaClientes.stream().map(x -> getClientExtra(x)).collect(Collectors.toList());
	}

	public ClienteWithExtraInfo getClientExtra(Cliente c) {

		ModelMapper modelMapper = new ModelMapper();
		ClienteWithExtraInfo clientExtra = modelMapper.map(c, ClienteWithExtraInfo.class);

		int basico = (minimoesperanzavida - c.getEdad()) >= 0 ? minimoesperanzavida - c.getEdad() : c.getEdad();
		int maximo = (maximoesperanzavida - c.getEdad()) >= 0 ? maximoesperanzavida - c.getEdad() : c.getEdad();
		int valorEntero = (int) Math.floor(Math.random() * (basico - maximo + 1) + maximo);
		LocalDate fechamuerte = LocalDate.now();

		clientExtra.setFechaMuerte(fechamuerte.plusYears(valorEntero));

		return clientExtra;
	}

}
