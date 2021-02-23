package microservice.models;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

public class Cliente {
	@NotNull
	private int edad;
	@NotNull
	private String nombre;
	@NotNull
	private String apellido;
	@NotNull
	private LocalDate fechaNacimiento;

	public Cliente(int edad, String nombre, String apellido, LocalDate fechaNacimiento) {
		super();
		this.edad = edad;
		this.nombre = nombre;
		this.apellido = apellido;
		this.fechaNacimiento = fechaNacimiento;
	}

	public Cliente() {

	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

}
