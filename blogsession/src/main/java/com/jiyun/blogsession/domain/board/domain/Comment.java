package com.jiyun.blogsession.domain.board.domain;

import com.jiyun.blogsession.domain.account.domain.Account;
import com.jiyun.blogsession.domain.board.domain.Post;
import com.jiyun.blogsession.global.entity.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Entity//해당 클래스에 있는 내부변수에 모두 @Column을 내부적으로 포함 -> 옵셥없으면 생략 가능
@NoArgsConstructor(access = AccessLevel.PROTECTED) //기본 생성자의 접근 제어를 PROTECTED로 설정해놓게 되면 무분별한 객체 생성에 대해 한번 더 체크할 수 있는 수단
@DynamicInsert//status 기본값 유지를 위해
@Getter
public class Comment extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "comment_id")
	private Long id;

	@Column(length = 1000)
	private String content;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "account_id", updatable = false)
	private Account writer;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "post_id", updatable = false)
	private Post post;


	// 연관관계 편의 메소드
	protected void setPost(Post post) {
		this.post = post;
	}

	@Builder
	public Comment(String content, Account writer, Post post) {
		this.content = content;
		this.writer = writer;
		this.post = post;
	}

	public void updateComment(String content) {
		this.content = content;
	}


	// 연관관계 편의 메소드

}
