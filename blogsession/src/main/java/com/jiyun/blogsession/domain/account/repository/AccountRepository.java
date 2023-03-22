package com.jiyun.blogsession.domain.account.repository;

import com.jiyun.blogsession.domain.account.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
	Boolean existsByEmail(String email);

	Optional<Account> findByEmail(String email);
}
