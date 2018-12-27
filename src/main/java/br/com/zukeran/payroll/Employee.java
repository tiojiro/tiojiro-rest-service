package br.com.zukeran.payroll;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@Entity
class Employee {

	@Id
	@GeneratedValue
	@ApiModelProperty(notes = "ID do Employee")
	private Long id;
	//Atributos que não possuem o Get e Set explicito será criado automaticamento pelo lombok (@Data)
	@ApiModelProperty(notes = "Primeiro nome do Employee")
	private String firstName;
	@ApiModelProperty(notes = "Último nome do Employee")
	private String lastName;
	@ApiModelProperty(notes = "Regra do Employee")
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