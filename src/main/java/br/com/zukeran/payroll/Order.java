package br.com.zukeran.payroll;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

enum Status {
	IN_PROGRESS,
	COMPLETED,
	CANCELLED;
}

@Entity
@Data
@Table(name = "CUSTOMER_ORDER")
class Order {

	private @Id @GeneratedValue Long id;

	private String description;
	private Status status;
	
	public Order () {
		
	}

	Order(String description, Status status) {

		this.description = description;
		this.status = status;
	}
}