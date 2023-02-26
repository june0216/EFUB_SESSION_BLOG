package com.jiyun.blogsession.domain.board.dto.request;


import com.jiyun.blogsession.domain.account.domain.Account;
import com.jiyun.blogsession.domain.board.domain.Post;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostRequestDto {
	@NotNull(message = "작성자는 필수로 입력되어야 합니다.")
	private Long accountId;
	@NotBlank(message = "제목은 필수로 입력되어야 합니다.")//해당 값이 null이 아니고, 공백(""과 " " 모두 포함)이 아닌지 검증
	private String title;

	@NotNull(message = "내용은 필수로 입력되어야 합니다.")//공백 또는 빈칸이 들어올 수 있음, Null이 아닌지만 체크
	private String content;

	@Builder
	public PostRequestDto(Long accountId, String title, String content) {
		this.accountId = accountId;
		this.title = title;
		this.content = content;
	}

	public Post toEntity(Account writer) {
		return Post.builder()
				.title(title)
				.content(content)
				.writer(writer)
				.build();
	}
}
