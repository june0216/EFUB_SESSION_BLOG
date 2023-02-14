package com.jiyun.blogsession.domain.account.service;

import com.jiyun.blogsession.domain.account.domain.Account;
import com.jiyun.blogsession.domain.account.dto.ProfileUpdateRequestDto;
import com.jiyun.blogsession.domain.account.dto.SignUpRequestDto;
import com.jiyun.blogsession.domain.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service//서비스 레이어, 내부에서 자바 로직을 처리함
@RequiredArgsConstructor //final 키워드가 붙은 필드에 대해 생성자 자동 생성
public class AccountService {
	private final AccountRepository accountRepository;
	//private final BCryptPasswordEncoder passwordEncoder;

	@Transactional //TODO:readOnly 적용
	public Account findById(Long id) {
		return accountRepository.findById(id)
				.orElseThrow(() -> new RuntimeException());
	}

	@Transactional//TODO:readOnly 적용
	public boolean isExistedEmail(String email){
		return accountRepository.existsByEmail(email);
	}

	@Transactional
	public Long signUp(SignUpRequestDto requestDto){
		if (isExistedEmail(requestDto.getEmail())){
			throw new IllegalArgumentException();
		}
		Account account = accountRepository.save(requestDto.toEntity());
		return account.getAccountId();
	}

	@Transactional
	public Long update(Long accountId, ProfileUpdateRequestDto requestDto){
		Account account = findById(accountId);
		account.updateAccount(requestDto.getBio());
		return account.getAccountId();
	}

}
