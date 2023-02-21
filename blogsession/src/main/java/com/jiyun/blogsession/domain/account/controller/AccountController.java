package com.jiyun.blogsession.domain.account.controller;

import com.jiyun.blogsession.domain.account.domain.Account;
import com.jiyun.blogsession.domain.account.dto.AccountResponseDto;
import com.jiyun.blogsession.domain.account.dto.AccountUpdateRequestDto;
import com.jiyun.blogsession.domain.account.dto.SignUpRequestDto;
import com.jiyun.blogsession.domain.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {
	private final AccountService accountService;

	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public AccountResponseDto signUp(@RequestBody @Valid final SignUpRequestDto requestDto) {
		Long id = accountService.signUp(requestDto);
		Account findAccount = accountService.findById(id);
		return new AccountResponseDto(findAccount);
	}
	@GetMapping("/{accountId}")
	@ResponseStatus(value = HttpStatus.OK)
	public AccountResponseDto getAccount(@PathVariable Long accountId)
	{
		Account findAccount = accountService.findById(accountId);
		return new AccountResponseDto(findAccount);
	}


	@PatchMapping("/{accountId}/profile")
	@ResponseStatus(value = HttpStatus.OK)
	public AccountResponseDto update(@PathVariable final Long accountId, @RequestBody @Valid final AccountUpdateRequestDto requestDto) {
		Long id = accountService.update(accountId,requestDto);
		Account findAccount = accountService.findById(id);
		return new AccountResponseDto(findAccount);
	}

	@PatchMapping("/{accountId}/withdraw/")
	@ResponseStatus(value = HttpStatus.OK)
	public void withdraw(@PathVariable long accountId)
	{
		accountService.withdraw(accountId);


	}

	@DeleteMapping("/{accountId}/withdraw/")
	@ResponseStatus(value = HttpStatus.OK)
	public void delete(@PathVariable long accountId)
	{
		accountService.delete(accountId);

	}

}
