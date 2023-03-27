package com.jiyun.blogsession.domain.board.service;

import com.jiyun.blogsession.domain.account.domain.Account;
import com.jiyun.blogsession.domain.account.service.AccountService;
import com.jiyun.blogsession.domain.board.domain.Comment;
import com.jiyun.blogsession.domain.board.domain.Post;
import com.jiyun.blogsession.domain.board.dto.request.AccountInfoRequestDto;
import com.jiyun.blogsession.domain.board.dto.request.CommentRequestDto;
import com.jiyun.blogsession.domain.board.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {
	private final CommentRepository commentRepository;

	private final PostService postService;
	private final AccountService accountService;


	public Long create(CommentRequestDto requestDto, Long postId) {
		Account account = accountService.findById(requestDto.getAccountId());
		Post post = postService.findById(postId);
		Comment comment = commentRepository.save(requestDto.toEntity(post, account));
		return comment.getId();
	}

	public void update(CommentRequestDto requestDto, Long commentId) {
		Comment comment = findById(commentId);
		checkValidMember(requestDto.getAccountId(), comment.getWriter().getAccountId());
		comment.updateComment(requestDto.getContent());
	}

	public void delete(Long commentId, AccountInfoRequestDto requestDto) {
		Comment comment = findById(commentId);
		checkValidMember(requestDto.getAccountId(), comment.getWriter().getAccountId());
		commentRepository.delete(comment);
	}

	private void checkValidMember(Long currentAccountId, Long tagetAccountId){
		if(currentAccountId != tagetAccountId){
			throw new IllegalArgumentException();
		}
	}

	// 연관관계 편의 메소드를 사용한 코드
	@Transactional(readOnly = true)
	public List<Comment> findByPostObj(Long postId) {
		Post post = postService.findById(postId);
		List<Comment> commentList = post.getCommentList();
		log.info("post.getCommentList = {}", commentList);
		return commentList;
	}


	@Transactional(readOnly = true)
	public Comment findById(Long commentId) {
		return commentRepository.findById(commentId)
				.orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + commentId));
	}

	@Transactional(readOnly = true)
	public List<Comment> findByWriter(Account author) {
		return commentRepository.findByWriter(author);
	}

	@Transactional(readOnly = true)
	public List<Comment> findByPost(Long postId) {
		Post post = postService.findById(postId);
		return commentRepository.findByPost(post);
	}

}
