package com.example.demo;

import org.springframework.data.repository.CrudRepository;
import com.example.demo.Account;

// This will be AUTO IMPLEMENTED by Spring into a Bean called accountRepository
 interface AccountRepository extends CrudRepository<Account, Integer> {
}
