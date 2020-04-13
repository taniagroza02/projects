package com.example.demo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
 class Account {

	private @Id @GeneratedValue int id;
	private String name;
	private float balance;
	
	Account(){}
	
	Account(String name, float balance) {
	    this.name = name;
	    this.balance = balance;
	  }
	
	public void credit (float amount) {
		this.balance = balance+amount;
	}
	public void debit (float amount) {
		this.balance = balance-amount;
	}
	
}