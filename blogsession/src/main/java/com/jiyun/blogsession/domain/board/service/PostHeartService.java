package com.jiyun.blogsession.domain.board.service;

import com.jiyun.blogsession.domain.account.domain.Account;
import com.jiyun.blogsession.domain.account.service.AccountService;
import com.jiyun.blogsession.domain.board.domain.Comment;
import com.jiyun.blogsession.domain.board.domain.Post;
import com.jiyun.blogsession.domain.board.domain.PostHeart;
import com.jiyun.blogsession.domain.board.dto.request.AccountInfoRequestDto;
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


	public void create(Long postId, AccountInfoRequestDto requestDto) {
		Post post = postService.findById(postId);
		Account account = accountService.findById(requestDto.getAccountId());
		if (isExistsByWriterAndPost(account, post)) {
			throw new RuntimeException("이미 좋아요를 눌렀습니다.");
		}
		PostHeart postHeart = PostHeart.builder()
				.post(post)
				.account(account)
				.build();
		postHeartRepository.save(postHeart);
	}

	public void delete(Long postHeartId, Long accountId) {
		PostHeart postHeart = findById(postHeartId);
		checkValidMember(accountId, postHeart.getWriter().getAccountId());
		postHeartRepository.delete(postHeart);
	}
	private void checkValidMember(Long currentAccountId, Long tagetAccountId){
		if(currentAccountId != tagetAccountId){
			throw new IllegalArgumentException();
		}
	}

	public boolean isHeart(Long accountId, Post post){
		Account account = accountService.findById(accountId);
		return isExistsByWriterAndPost(account, post);
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
	public PostHeart findById(Long postHeartId) {
		return postHeartRepository.findById(postHeartId)
				.orElseThrow(() -> new IllegalArgumentException("해당 좋아요가 없습니다. id=" + postHeartId));
	}
	@Transactional(readOnly = true)
	public List<Post> findLikePostList(List<PostHeart> postLikeList) {
		return postLikeList.stream()
				.map(PostHeart::getPost)
				.collect(Collectors.toList());
	}



}
