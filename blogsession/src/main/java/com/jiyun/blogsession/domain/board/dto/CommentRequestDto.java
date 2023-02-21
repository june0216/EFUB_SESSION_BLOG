package com.jiyun.blogsession.domain.board.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentRequestDto {
	@NotNull(message = "내용은 필수로 입력되어야 합니다.")// 공백까지는 허용
	private String content;

	@Builder
	public CommentRequestDto(String content) {
		this.content = content;
	}
}
