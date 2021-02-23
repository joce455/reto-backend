package microservice.operations;

import java.time.LocalDate;
import java.util.ArrayList;
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
	private int minimoesperanzavida;

	@Value("${maximo.esperanza}")
	private int maximoesperanzavida;

	

	List<Cliente> clientes = new ArrayList<>();

	@Override
	public double getPromData() {
		return clientes.stream().mapToDouble((x) -> x.getEdad()).summaryStatistics().getAverage();
	}

	@Override
	public double getVarEstandarData(double promedio) {
		return Math.sqrt(
				(clientes.stream().mapToDouble((x) -> Math.pow((x.getEdad() - promedio), 2)).sum()) / clientes.size());
	}

	@Override
	public List<ClienteWithExtraInfo> getDetailsClients() {
		return clientes.stream().map(x -> getClientExtra(x)).collect(Collectors.toList());
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

	@Override
	public int addClient(int edad, String nombre, String apellido, LocalDate fechaNacimiento) {

		Cliente cliente = new Cliente(edad, nombre, apellido, fechaNacimiento);

		clientes.add(cliente);
		
		return clientes.size();
	}

	public int getMinimoesperanzavida() {
		return minimoesperanzavida;
	}

	public void setMinimoesperanzavida(int minimoesperanzavida) {
		this.minimoesperanzavida = minimoesperanzavida;
	}

	public int getMaximoesperanzavida() {
		return maximoesperanzavida;
	}

	public void setMaximoesperanzavida(int maximoesperanzavida) {
		this.maximoesperanzavida = maximoesperanzavida;
	}
}
