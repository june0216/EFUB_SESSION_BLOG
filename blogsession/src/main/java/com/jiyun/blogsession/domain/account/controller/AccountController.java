package com.jiyun.blogsession.domain.account.controller;

import com.jiyun.blogsession.domain.account.domain.Account;
import com.jiyun.blogsession.domain.account.dto.*;
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
	public AccountResponseDto getAccount(@PathVariable final Long accountId)
	{
		Account findAccount = accountService.findById(accountId);
		return new AccountResponseDto(findAccount);
	}

	@PostMapping("/login")
	@ResponseStatus(value = HttpStatus.OK)
	public LoginResponseDto login(@RequestBody final LoginRequestDto requestDto)
	{
		Long accountId = accountService.login(requestDto);
		return new LoginResponseDto(accountId);
	}


	@PatchMapping("/profile/{accountId}")
	@ResponseStatus(value = HttpStatus.OK)
	public AccountResponseDto update(@PathVariable final Long accountId, @RequestBody @Valid final AccountUpdateRequestDto requestDto) {
		Long id = accountService.update(accountId,requestDto);
		Account findAccount = accountService.findById(id);
		return new AccountResponseDto(findAccount);
	}

	@PatchMapping("/{accountId}")
	@ResponseStatus(value = HttpStatus.OK)
	public String withdraw(@PathVariable final Long accountId)
	{
		accountService.withdraw(accountId);
		return "성공적으로 탈퇴가 완료되었습니다";

	}

	@DeleteMapping("/{accountId}")
	@ResponseStatus(value = HttpStatus.OK)
	public String delete(@PathVariable final Long accountId)
	{
		accountService.delete(accountId);
		return "성공적으로 탈퇴가 완료되었습니다";

	}

}
