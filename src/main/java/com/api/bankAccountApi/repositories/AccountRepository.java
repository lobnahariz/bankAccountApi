package com.api.bankAccountApi.repositories;

import org.springframework.data.repository.CrudRepository;

import com.api.bankAccountApi.entities.Account;

public interface AccountRepository extends CrudRepository<Account, String> {

}
