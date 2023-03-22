package com.jiyun.blogsession.domain.board.controller;

import com.jiyun.blogsession.domain.account.domain.Account;
import com.jiyun.blogsession.domain.account.service.AccountService;
import com.jiyun.blogsession.domain.board.domain.Comment;
import com.jiyun.blogsession.domain.board.domain.CommentHeart;
import com.jiyun.blogsession.domain.board.dto.request.CommentRequestDto;
import com.jiyun.blogsession.domain.board.dto.request.HeartRequestDto;
import com.jiyun.blogsession.domain.board.dto.response.CommentListResponseDto;
import com.jiyun.blogsession.domain.board.dto.response.CommentResponseDto;
import com.jiyun.blogsession.domain.board.service.CommentHeartService;
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
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {
	private final CommentService commentService;
	private final AccountService accountService;
	private final CommentHeartService commentHeartService;

	@GetMapping("/{commentId}")
	@ResponseStatus(value = HttpStatus.OK)
	public CommentResponseDto readComment(@PathVariable final Long commentId, @RequestParam final Long accountId) {
		Comment comment = commentService.findById(commentId);
		Account account = accountService.findById(accountId);
		Integer heartCount = commentHeartService.countCommentHeart(comment);
		boolean isHeart = commentHeartService.isExistsByWriterAndComment(account, comment);

		CommentResponseDto responseDto = CommentResponseDto.of(comment);
		responseDto.uploadHeart(heartCount, isHeart);
		return responseDto;
	}

	@PutMapping("/{commentId}")
	@ResponseStatus(value = HttpStatus.OK)
	public CommentResponseDto updateComment(@PathVariable final Long commentId, @RequestBody @Valid final CommentRequestDto requestDto) {
		commentService.update(requestDto, commentId);
		Comment comment = commentService.findById(commentId);
		Account account = accountService.findById(requestDto.getAccountId());
		Integer heartCount = commentHeartService.countCommentHeart(comment);
		boolean isHeart = commentHeartService.isExistsByWriterAndComment(account, comment);

		CommentResponseDto responseDto = CommentResponseDto.of(comment);
		responseDto.uploadHeart(heartCount, isHeart);
		return responseDto;
	}

	@DeleteMapping("/{commentId}")
	@ResponseStatus(value = HttpStatus.OK)
	public String deleteComment(@PathVariable final Long commentId) {
		commentService.delete(commentId);
		return "성공적으로 삭제되었습니다.";
	}

	@PostMapping("/{commentId}/hearts")
	@ResponseStatus(value = HttpStatus.CREATED)
	public String createCommentLike(@PathVariable final Long commentId, @RequestBody final HeartRequestDto requestDto) {
		commentHeartService.create(commentId, requestDto.getAccountId());
		return "좋아요를 눌렀습니다.";
	}

	@DeleteMapping("/{commentId}/hearts")
	@ResponseStatus(value = HttpStatus.OK)
	public String deleteCommentLike(@PathVariable final Long commentId, @RequestParam final Long accountId) {
		commentHeartService.delete(commentId, accountId);
		return "좋아요가 취소되었습니다.";
	}


}
