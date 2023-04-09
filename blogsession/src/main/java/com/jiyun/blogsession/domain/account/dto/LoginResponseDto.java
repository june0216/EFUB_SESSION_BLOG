package com.jiyun.blogsession.domain.account.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginResponseDto {
	private  Long accountId;

	public LoginResponseDto(Long accountId) {
		this.accountId = accountId;
	}
}
