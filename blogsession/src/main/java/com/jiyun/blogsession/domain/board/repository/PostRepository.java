package com.jiyun.blogsession.domain.board.repository;

import com.jiyun.blogsession.domain.account.domain.Account;
import com.jiyun.blogsession.domain.board.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
	List<Post> findByWriter(Account account);
	List<Post> findAllByOrderByIdDesc();
}
