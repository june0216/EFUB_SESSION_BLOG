package com.jiyun.blogsession.domain.account.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class AccountUpdateRequestDto {
	@NotBlank(message = "회원 아이디는 필수로 입력해야 합니다.")
	private Long accountId;
	private String bio;
	@NotBlank(message = "닉네임은 필수값입니다. ")
	private String nickname;

	@Builder
	public AccountUpdateRequestDto(Long accountId, String bio, String nickname) {
		this.accountId = accountId;
		this.bio = bio;
		this.nickname = nickname;
	}
}
