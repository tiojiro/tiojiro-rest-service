package br.com.zukeran.payroll;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
class Employee {

	private @Id @GeneratedValue Long id;
	//Atributos que não possuem o Get e Set explicito será criado automaticamento pelo lombok (@Data)
	private String firstName;
	private String lastName;
	private String role;
	
	public Employee() {
	}

	Employee(String firstName, String lastName, String role) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.role = role;
	}
	
	//=================================================
	//Criado Get e Set para para suportar a versão antiga onde só existia o atributo name.
	public String getName() {
		return this.firstName + " " + this.lastName;
	}

	public void setName(String name) {
		String[] parts =name.split(" ");
		this.firstName = parts[0];
		this.lastName = parts[1];
	}
	//=================================================
}