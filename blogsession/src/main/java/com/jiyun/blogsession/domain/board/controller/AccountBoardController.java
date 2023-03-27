package com.jiyun.blogsession.domain.board.controller;

import com.jiyun.blogsession.domain.account.domain.Account;
import com.jiyun.blogsession.domain.account.service.AccountService;
import com.jiyun.blogsession.domain.board.domain.Comment;
import com.jiyun.blogsession.domain.board.domain.Post;
import com.jiyun.blogsession.domain.board.dto.response.CommentListResponseDto;
import com.jiyun.blogsession.domain.board.dto.response.PostListResponseDto;
import com.jiyun.blogsession.domain.board.dto.response.PostResponseDto;
import com.jiyun.blogsession.domain.board.service.CommentService;
import com.jiyun.blogsession.domain.board.service.PostHeartService;
import com.jiyun.blogsession.domain.board.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/accounts/{accountId}")
@RequiredArgsConstructor
public class AccountBoardController {
	private final PostService postService;
	private final CommentService commentService;
	private final AccountService accountService;
	private final PostHeartService postHeartService;

	@GetMapping("/posts")//작성자 별 글 조회
	@ResponseStatus(value = HttpStatus.OK)
	public PostListResponseDto readPostList(@PathVariable Long accountId) {
		List<Post> postList = postService.findByWriter(accountId);
		return PostListResponseDto.of(postList);
	}

	@GetMapping("/comments")//작성자 별 댓글 조회
	@ResponseStatus(value = HttpStatus.OK)
	public CommentListResponseDto.Account readCommentsList(@PathVariable final Long accountId) {
		Account account = accountService.findById(accountId);
		List<Comment> commentList = commentService.findByWriter(account);
		return CommentListResponseDto.Account.of(account.getNickname(), commentList);
	}


}
