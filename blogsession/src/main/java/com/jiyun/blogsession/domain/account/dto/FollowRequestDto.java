package com.jiyun.blogsession.domain.account.dto;

import com.jiyun.blogsession.domain.account.domain.Account;
import com.jiyun.blogsession.domain.account.domain.Follow;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FollowRequestDto {

	private Long followingId;

	@Builder
	public FollowRequestDto(Long followingId) {
		this.followingId = followingId;
	}

	public Follow toEntity(Account follower, Account following){//FollowRequestDto를 Follow entity로 변환해주는 책임은 FollowRequestDto에게 있다.
						   //그렇기 때문에 FollowRequestDto가 Follow로 변환해주는 로직이 필요하다.
		return Follow.builder()
				.follower(follower)
				.following(following)
				.build();
	}

}
