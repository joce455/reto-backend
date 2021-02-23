package microservice.operations;

import java.util.List;

import microservice.models.Cliente;
import microservice.models.ClienteWithExtraInfo;

public interface IClientOperations {
	public double getPromData(List<Cliente> listaClientes);

	public double getVarEstandarData(List<Cliente> listaClientes, double promedio);

	public List<ClienteWithExtraInfo> getDetailsClients(List<Cliente> listaClientes);
}
