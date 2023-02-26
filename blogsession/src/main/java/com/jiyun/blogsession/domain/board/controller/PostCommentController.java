package com.jiyun.blogsession.domain.board.controller;

import com.jiyun.blogsession.domain.board.domain.Comment;
import com.jiyun.blogsession.domain.board.dto.request.CommentRequestDto;
import com.jiyun.blogsession.domain.board.dto.response.CommentListResponseDto;
import com.jiyun.blogsession.domain.board.dto.response.CommentResponseDto;
import com.jiyun.blogsession.domain.board.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Parameter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/posts/{postId}/comments")
@RequiredArgsConstructor
public class PostCommentController {
	private final CommentService commentService;
	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public CommentResponseDto createComment(@PathVariable final Long postId,
											@RequestBody @Valid final CommentRequestDto requestDto
									   ) {

		Long commentId = commentService.create(requestDto, postId);
		Comment comment = commentService.findById(commentId);
		return CommentResponseDto.of(comment);
	}


	@GetMapping
	@ResponseStatus(value = HttpStatus.OK)
	public CommentListResponseDto.Post readCommentList(@PathVariable final Long postId) {

		List<Comment> commentList = commentService.findByPost(postId);
		return CommentListResponseDto.Post.of(postId, commentList);
	}
}
