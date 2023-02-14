package com.jiyun.blogsession.domain.account.dto;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccountRequestDto {
	private String email;
	private String nickname;
	private String bio;

	@Builder
	public AccountRequestDto(String email, String nickname, String bio) {
		this.email = email;
		this.nickname = nickname;
		this.bio = bio;
	}
}
