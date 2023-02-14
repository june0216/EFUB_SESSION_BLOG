package com.jiyun.blogsession.domain.account.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class ProfileUpdateRequestDto {
	private String bio;
	private String nickname;

	@Builder
	public ProfileUpdateRequestDto(String bio, String nickname) {
		this.bio = bio;
		this.nickname = nickname;
	}

}
