package microservices;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

import microservice.models.ClienteWithExtraInfo;
import microservice.operations.IClientOperations;
import microservice.operations.Operations;

@RunWith(MockitoJUnitRunner.class)
public class TestController {

	DateTimeFormatter formatter;

	int maxYearsVida, minYearsVida;

	@Spy
	private final IClientOperations operations = new Operations();

	@Before
	public void setup() {

		minYearsVida = 75;
		maxYearsVida = 85;
		ReflectionTestUtils.setField(operations, "minimoesperanzavida", minYearsVida);
		ReflectionTestUtils.setField(operations, "maximoesperanzavida", maxYearsVida);

		formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		String date1 = "29-11-1988";
		LocalDate fechaNacimiento1 = LocalDate.parse(date1, formatter);

		operations.addClient(30, "Jose", "Juarez", fechaNacimiento1);
		String date2 = "29-11-2015";
		LocalDate fechaNacimiento2 = LocalDate.parse(date2, formatter);

		operations.addClient(30, "Pedro", "Alvarez", fechaNacimiento2);

	}

	@Test
	public void shouldAddClient() {
		String date = "29-11-1988";
		LocalDate fechaNacimiento = LocalDate.parse(date, formatter);
		int result = operations.addClient(30, "Jose", "Perez", fechaNacimiento);
		Assert.assertEquals(3, result);
	}

	@Test
	public void shouldReturnPromandVarEstandar() {
		double promData = operations.getPromData();
		double varianza = operations.getVarEstandarData(promData);
		Assert.assertEquals(30.0, promData, 0.0);
		Assert.assertEquals(0.0, varianza, 0.0);

	}

	@Test
	public void shoulCheckEsperanzaVida() {
		ClienteWithExtraInfo client1 = operations.getDetailsClients().get(0);
		ClienteWithExtraInfo client2 = operations.getDetailsClients().get(0);
		Period period1 = Period.between(client1.getFechaNacimiento(), client1.getFechaMuerte());
		Period period2 = Period.between(client2.getFechaNacimiento(), client2.getFechaMuerte());

		Assert.assertTrue(period1.getYears() >= minYearsVida && period1.getYears() <= maxYearsVida);
		Assert.assertTrue(period2.getYears() >= minYearsVida && period2.getYears() <= maxYearsVida);
	}
}
