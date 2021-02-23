package microservice.operations;

import java.time.LocalDate;
import java.util.List;
import microservice.models.ClienteWithExtraInfo;

public interface IClientOperations {
	
	public int addClient(int edad,String nombre,String apellido, LocalDate fechaNacimiento);
	
	public double getPromData();

	public double getVarEstandarData(double promedio);

	public List<ClienteWithExtraInfo> getDetailsClients();
	
	
}
