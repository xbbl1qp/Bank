package com.godel.test.bank.dao.persistence;

import com.godel.test.bank.dto.entity.Account;

import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, BigInteger> {

    Optional<Double> findBalanceByAccountId(BigInteger accountId);
}
