package com.jiyun.blogsession.domain.board.dto;

import com.jiyun.blogsession.domain.board.domain.Comment;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentResponseDto {

	private Long commentId;

	private Long postId;

	private String writerName;

	private String content;

	private LocalDateTime modifiedDate;

	public static CommentResponseDto of(Comment comment) {
		return CommentResponseDto.builder()
				.commentId(comment.getId())
				.postId(comment.getPost().getPostId())
				.writerName(comment.getWriter().getNickname())
				.content(comment.getContent())
				.modifiedDate(comment.getModifiedDate())
				.build();
	}
}
