package com.jiyun.blogsession.domain.board.domain;

import com.jiyun.blogsession.domain.account.domain.Account;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostHeart {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "post_heart_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@NotNull(message = "게시글은 필수로 입력되어야 합니다.")
	@JoinColumn(name = "post_id", updatable = false)
	private Post post;

	@ManyToOne(fetch = FetchType.LAZY)
	@NotNull(message = "작성자는 필수로 입력되어야 합니다.")
	@JoinColumn(name = "account_id", updatable = false)
	private Account writer;


	@Builder
	public PostHeart(Post post, Account account) {
		this.post = post;
		this.writer = account;

	}




}
