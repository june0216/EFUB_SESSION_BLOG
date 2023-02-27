package com.jiyun.blogsession.domain.board.service;

import com.jiyun.blogsession.domain.account.domain.Account;
import com.jiyun.blogsession.domain.account.service.AccountService;
import com.jiyun.blogsession.domain.board.domain.Post;
import com.jiyun.blogsession.domain.board.domain.PostHeart;
import com.jiyun.blogsession.domain.board.repository.PostHeartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;



@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PostHeartService {

	private final PostHeartRepository postHeartRepository;
	private final PostService postService;
	private final AccountService accountService;

	public void create(Long postId, Long accountId) {
		Account account = accountService.findById(accountId);
		Post post = postService.findById(postId);
		if (isExistsByWriterAndPost(account, post)) {
			throw new RuntimeException("이미 좋아요를 눌렀습니다.");
		}
		PostHeart postHeart = PostHeart.builder()
				.post(post)
				.account(account)
				.build();
		postHeartRepository.save(postHeart);
	}

	public void delete(Long postId, Long accountId) {
		Post post = postService.findById(postId);
		Account account = accountService.findById(accountId);
		PostHeart postLike = postHeartRepository.findByWriterAndPost(account, post)
				.orElseThrow(() -> new RuntimeException("좋아요가 존재하지 않습니다."));
		postHeartRepository.delete(postLike);
	}

	@Transactional(readOnly = true)
	public boolean isExistsByWriterAndPost(Account account, Post post) {
		return postHeartRepository.existsByWriterAndPost(account, post);
	}



	@Transactional(readOnly = true)
	public Integer countPostHeart(Post post) {
		Integer count = postHeartRepository.countByPost(post);
		return count;
	}

	@Transactional(readOnly = true)
	public List<PostHeart> findByWriter(Account account) {
		return postHeartRepository.findByWriter(account);
	}

	@Transactional(readOnly = true)
	public List<Post> findLikePostList(List<PostHeart> postLikeList) {
		return postLikeList.stream()
				.map(PostHeart::getPost)
				.collect(Collectors.toList());
	}



}
