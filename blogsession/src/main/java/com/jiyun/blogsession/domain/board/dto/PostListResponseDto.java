package com.jiyun.blogsession.domain.board.dto;

import com.jiyun.blogsession.domain.board.domain.Post;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PostListResponseDto {

	private List<SinglePost> posts;
	private Integer count;

	@Getter
	public static class SinglePost{// 단일 객체글 생성
		private Long postId;
		private String writerName;
		private String title;
		private String content;
		private LocalDateTime createDate;
		private LocalDateTime modifiedDate;

		public SinglePost(Post post) {
			this.postId = getPostId();
			this.writerName = getWriterName();
			this.title = getTitle();
			this.content = getContent();
			this.createDate = getCreateDate();
			this.modifiedDate = getModifiedDate();
		}

		public static SinglePost of(Post post) {
			return new SinglePost(post);
		}
	}


	public static PostListResponseDto of(List<Post> postList) {
		return PostListResponseDto.builder()
				.posts(postList.stream().map(SinglePost::of).collect(Collectors.toList()))
				.count(postList.size())
				.build();
	}


}
