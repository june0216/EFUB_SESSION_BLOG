package com.jiyun.blogsession.domain.board.domain;

import com.jiyun.blogsession.domain.account.domain.Account;
import com.jiyun.blogsession.global.entity.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity//해당 클래스에 있는 내부변수에 모두 @Column을 내부적으로 포함 -> 옵셥없으면 생략 가능
@NoArgsConstructor(access = AccessLevel.PROTECTED) //기본 생성자의 접근 제어를 PROTECTED로 설정해놓게 되면 무분별한 객체 생성에 대해 한번 더 체크할 수 있는 수단
@Getter
public class Post extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "post_id")
	private Long postId;

	@Column(length = 500, nullable = false)
	private String title;

	@Column(columnDefinition = "TEXT")// @NotNull은 @Column(nullable=false)의 역할도 같이 하므로 생략
	private String content;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "account_id", updatable = false)
	private Account writer;

	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true) //연관관계 주인인 Member가 외래키를 가지고 있음 (다대일 양방향 -> 연관관계 주인이 외래키 가짐)
	private List<Comment> commentList = new ArrayList<>();



	@Builder
	public Post(String title, String content, Account writer) {
		this.title = title;
		this.content = content;
		this.writer = writer;
	}

	public void updatePost(String title, String content)
	{
		this.title = title;
		this.content = content;
	}

}
