package com.jiyun.blogsession.domain.board.repository;

import com.jiyun.blogsession.domain.account.domain.Account;
import com.jiyun.blogsession.domain.board.domain.Post;
import com.jiyun.blogsession.domain.board.domain.PostHeart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostHeartRepository extends JpaRepository<PostHeart, Long> {
	Integer countByPost(Post post);
	List<PostHeart> findByWriter(Account account);
	boolean existsByWriterAndPost(Account account, Post post);
	Optional<PostHeart> findByWriterAndPost(Account account, Post post);
}
