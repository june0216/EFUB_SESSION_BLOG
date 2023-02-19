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
	public ResponseEntity<AccountResponseDto> signUp(@RequestBody @Valid final SignUpRequestDto requestDto) {
		Long id = accountService.signUp(requestDto);
		Account findAccount = accountService.findById(id);
		return ResponseEntity.ok()
				.body(new AccountResponseDto(findAccount));
	}

	@PatchMapping("/profile")
	public ResponseEntity<AccountResponseDto> update(@RequestBody @Valid final AccountUpdateRequestDto requestDto) {
		Long id = accountService.update(requestDto);
		Account findAccount = accountService.findById(id);
		return ResponseEntity.ok()
				.body(new AccountResponseDto(findAccount));
	}

	@PatchMapping("/withdraw")
	public ResponseEntity<String> withdraw(@RequestParam Long accountID)
	{
		accountService.withdraw(accountID);
		return ResponseEntity.ok()
				.body("성공적으로 탈퇴가 완료되었습니다.");

	}

	@DeleteMapping("/withdraw/{accountId}")
	public ResponseEntity<String> delete(@PathVariable long accountId)
	{
		accountService.delete(accountId);
		return ResponseEntity.ok()
				.body("성공적으로 탈퇴가 완료되었습니다.");

	}

}
