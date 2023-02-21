package com.jiyun.blogsession.domain.board.service;

import com.jiyun.blogsession.domain.account.domain.Account;
import com.jiyun.blogsession.domain.account.service.AccountService;
import com.jiyun.blogsession.domain.board.domain.Post;
import com.jiyun.blogsession.domain.board.dto.PostRequestDto;
import com.jiyun.blogsession.domain.board.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PostService {
	private final PostRepository postRepository;
	private final AccountService accountService; //서비스 안에서 다른 서비스 호출 가능

	@Transactional(readOnly = true)
	public List<Post> findAllDesc() {
		return postRepository.findAllByOrderByIdDesc();
	}


	@Transactional(readOnly = true)
	public List<Post> findByWriter(Long accountId) {
		Account account = accountService.findById(accountId);
		return postRepository.findByWriter(account);
	}



	public Long create(PostRequestDto requestDto) {
		Account account = accountService.findById(requestDto.getAccountId());
		Post board = postRepository.save(requestDto.toEntity(account));
		return board.getBoardId();
	}


	public void update(Long postId, PostRequestDto requestDto) {
		Post board = findById(postId);
		board.updatePost(requestDto.getTitle(), requestDto.getContent());
	}

	public void delete(Long boardId) {
		Post board = findById(boardId);
		postRepository.delete(board);
	}

	@Transactional(readOnly = true)
	public Post findById(Long boardId) {
		return postRepository.findById(boardId)
				.orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + boardId));
	}
}
