package com.jiyun.blogsession.domain.board.dto.response;

import com.jiyun.blogsession.domain.board.domain.Post;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PostResponseDto {
	private Long postId;
	private String writerName;
	private String title;
	private String content;
	private Integer heartCount;
	private Boolean isHeart;
	private LocalDateTime createdDate;
	private LocalDateTime modifiedDate;
//TODO: 좋아요, 댓글 추가

	public static PostResponseDto of(Post board) {
		return PostResponseDto.builder()
				.postId(board.getPostId())
				.title(board.getTitle())
				.content(board.getContent())
				.writerName(board.getWriter().getNickname())
				.createdDate(board.getCreatedDate())
				.modifiedDate(board.getModifiedDate())
				.build();
	}
	public void uploadHeart(Integer heartCount, boolean isHeart) {
		this.heartCount = heartCount;
		this.isHeart = isHeart;

	}

}
