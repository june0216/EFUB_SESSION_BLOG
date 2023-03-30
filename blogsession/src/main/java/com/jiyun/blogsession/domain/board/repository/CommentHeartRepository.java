package com.jiyun.blogsession.domain.board.repository;

import com.jiyun.blogsession.domain.account.domain.Account;
import com.jiyun.blogsession.domain.board.domain.Comment;
import com.jiyun.blogsession.domain.board.domain.CommentHeart;
import com.jiyun.blogsession.domain.board.domain.Post;
import com.jiyun.blogsession.domain.board.domain.PostHeart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentHeartRepository extends JpaRepository<CommentHeart, Long> {

	Integer countByComment(Comment comment);
	List<CommentHeart> findByWriter(Account account);
	boolean existsByWriterAndComment(Account account, Comment comment);
	Optional<CommentHeart> findByWriterAndComment(Account account, Comment comment);
}
