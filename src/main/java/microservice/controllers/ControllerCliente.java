package microservice.controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import microservice.models.Cliente;
import microservice.models.ClienteWithExtraInfo;
import microservice.operations.IClientOperations;

@RestController
public class ControllerCliente {

	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ControllerCliente.class);

	@Autowired
	IClientOperations operations;

	List<Cliente> clientes = new ArrayList<>();

	@ApiOperation(value = "Permite agregar un cliente" )
	@PostMapping(value = "/creacliente")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<String> crearCliente(@RequestParam("Edad") int edad, @RequestParam("Nombre") String nombre,
			@RequestParam("Apellido") String apellido,

			@ApiParam(name = "FechaNacimiento", type = "LocalDate", value = "Nacimiento del cliente en formato dd-MM-yyyy", example = "29-11-1988", required = true)
			@RequestParam("FechaNacimiento") @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate fechaNacimiento) {

		log.info("Creando cliente...");

		Cliente c = new Cliente(edad, nombre, apellido, fechaNacimiento);
		clientes.add(c);
		log.info("Cliente {} - {} creado con exito ...", nombre, apellido);

		return ResponseEntity.status(HttpStatus.CREATED)
				.body("Result operation:" + "Cliente agregado con exito" + "\nTotal clientes: " + clientes.size());
	}

	@ApiOperation(value = "Obtiene KPI's de promedio de edad y desviacion estandar")
	@GetMapping(value = "/kpideclientes")
	public ResponseEntity<String> getKpiClientes() {
		double promedio = operations.getPromData(clientes);
		double varEstandar = operations.getVarEstandarData(clientes, promedio);

		return ResponseEntity.status(HttpStatus.OK)
				.body("Promedio edad clientes: " + promedio + "\nDesviacion estandar: " + varEstandar);

	}

	@ApiOperation(value = "Lista todos los clientes")
	@GetMapping(value = "/listclientes")
	public ResponseEntity<List<ClienteWithExtraInfo>> getListClientes() {
		return ResponseEntity.ok(operations.getDetailsClients(clientes));

	}

}
