package com.example.demo;

class AccountNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	AccountNotFoundException(int id) {
		super("Could not find the account " + id);
	}
}
