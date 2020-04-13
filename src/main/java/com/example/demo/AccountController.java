package com.example.demo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/accounts")
class AccountController {

	@Autowired
	private AccountRepository accountRepository;

	@GetMapping("/all")
	Iterable<Account> getAllAccounts() {
		return accountRepository.findAll();
	}

	Account getAccount(int id) {
		return accountRepository.findById(id).orElseThrow(() -> new AccountNotFoundException(id));
	}

	@GetMapping("/{id}")
	Account getOneAccount(@PathVariable int id) {
		return getAccount(id);
	}

	@PostMapping("/add")
	ResponseEntity<Account> createNewAccount(@RequestBody Account newAccount) {
		Account entityModel = accountRepository.save(newAccount);
		return ResponseEntity.ok(entityModel);
	}

	@Transactional
	@PutMapping("/{id}")
	ResponseEntity<String> moveAmountBetweenAccounts(@RequestParam("id") int to_id, @RequestParam float amount, @PathVariable int id) {
		
		// check amount to be positive
		if (amount<0) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Amount should be positive");			
		}
		Account acntFrom = getAccount(id);
		Account acntTo = getAccount(to_id);

		// validate balance
		if (acntFrom.getBalance() < amount) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Insuficient funds on acount " + id);			
		} else {
			acntFrom.debit(amount);
			acntTo.credit(amount);
			accountRepository.save(acntFrom);
			accountRepository.save(acntTo);
			return ResponseEntity.status(HttpStatus.OK).body("Transaction completed");
		}
	}

	@DeleteMapping("/{id}")
	ResponseEntity<?> deleteAccount(@PathVariable int id) {
		getAccount(id);
		accountRepository.deleteById(id);
		return ResponseEntity.status(HttpStatus.OK).body("Account "+id +" deleted");

	}
}