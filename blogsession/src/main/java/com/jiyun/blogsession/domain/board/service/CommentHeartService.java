package com.jiyun.blogsession.domain.board.service;

import com.jiyun.blogsession.domain.account.domain.Account;
import com.jiyun.blogsession.domain.account.service.AccountService;
import com.jiyun.blogsession.domain.board.domain.Comment;
import com.jiyun.blogsession.domain.board.domain.CommentHeart;
import com.jiyun.blogsession.domain.board.domain.Post;
import com.jiyun.blogsession.domain.board.domain.PostHeart;
import com.jiyun.blogsession.domain.board.repository.CommentHeartRepository;
import com.jiyun.blogsession.domain.board.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CommentHeartService {
	private final CommentService commentService;
	private final CommentHeartRepository commentHeartRepository;


	private final AccountService accountService;

	public void create(Long commentId, Long accountId) {
		Account account = accountService.findById(accountId);
		Comment comment = commentService.findById(commentId);
		if (isExistsByWriterAndComment(account, comment)) {
			throw new RuntimeException("이미 좋아요를 눌렀습니다.");
		}
		CommentHeart commentHeart = CommentHeart.builder()
				.comment(comment)
				.account(account)
				.build();
		commentHeartRepository.save(commentHeart);
	}

	public void delete(Long commentId, Long accountId) {
		Comment comment = commentService.findById(commentId);
		Account account = accountService.findById(accountId);
		CommentHeart commentLike = commentHeartRepository.findByWriterAndComment(account, comment)
				.orElseThrow(() -> new RuntimeException("좋아요가 존재하지 않습니다."));
		commentHeartRepository.delete(commentLike);
	}

	@Transactional(readOnly = true)
	public boolean isExistsByWriterAndComment(Account account, Comment comment) {
		return commentHeartRepository.existsByWriterAndComment(account, comment);
	}



	@Transactional(readOnly = true)
	public Integer countPostHeart(Comment comment) {
		Integer count = commentHeartRepository.countByComment(comment);
		return count;
	}

	@Transactional(readOnly = true)
	public List<CommentHeart> findByWriter(Account account) {
		return commentHeartRepository.findByWriter(account);
	}

	@Transactional(readOnly = true)
	public List<Post> findLikePostList(List<PostHeart> postLikeList) {
		return postLikeList.stream()
				.map(PostHeart::getPost)
				.collect(Collectors.toList());
	}
}
