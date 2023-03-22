package com.jiyun.blogsession.domain.account.dto;

import com.jiyun.blogsession.domain.account.domain.Account;
import com.jiyun.blogsession.domain.account.domain.Follow;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FollowStatusResponseDto {
	private Long accountId;
	private String nickname;
	private String email;
	private String status;// enum으로 해도 됨

	@Builder
	public FollowStatusResponseDto(Account account, Boolean isFollow) {
		this.accountId = account.getAccountId();
		this.nickname = account.getNickname();
		this.email = account.getEmail();
		if(!isFollow){
			this.status = "UNFOLLOW";
		}else{
			this.status = "FOLLOW";
		}
	}
}
