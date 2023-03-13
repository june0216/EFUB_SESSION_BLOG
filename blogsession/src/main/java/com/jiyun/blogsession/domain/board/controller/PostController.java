package com.jiyun.blogsession.domain.board.controller;


import com.jiyun.blogsession.domain.board.domain.Post;
import com.jiyun.blogsession.domain.board.dto.request.HeartRequestDto;
import com.jiyun.blogsession.domain.board.dto.response.PostListResponseDto;
import com.jiyun.blogsession.domain.board.dto.request.PostRequestDto;
import com.jiyun.blogsession.domain.board.dto.response.PostResponseDto;
import com.jiyun.blogsession.domain.board.service.PostHeartService;
import com.jiyun.blogsession.domain.board.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Parameter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
	private final PostService postService;
	private final PostHeartService postHeartService;

	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public PostResponseDto create(@RequestBody @Valid final PostRequestDto requestDto) {
		Long id = postService.create(requestDto);
		Post post = postService.findById(id);
		return PostResponseDto.of(post);
	}

	@GetMapping
	@ResponseStatus(value = HttpStatus.OK)// 전체 조회
	public PostListResponseDto readBoardList() {
		List<Post> postList = postService.findAllDesc();
		return PostListResponseDto.of(postList);
	}

	@GetMapping("/{postId}")
	@ResponseStatus(value = HttpStatus.OK)// 글 1개 조회
	public PostResponseDto readBoard(@PathVariable Long postId) {
		Post post = postService.findById(postId);
		return PostResponseDto.of(post);
	}



	@PutMapping("/{postId}")
	@ResponseStatus(value = HttpStatus.OK)
	public PostResponseDto update(@PathVariable final Long postId, @RequestBody final PostRequestDto requestDto) {
		postService.update(postId, requestDto);
		Post post = postService.findById(postId);
		return PostResponseDto.of(post);
	}

	@DeleteMapping("/{postId}")
	@ResponseStatus(value = HttpStatus.OK)
	public String delete(@PathVariable final Long postId) {
		postService.delete(postId);
		return "성공적으로 삭제되었습니다.";
	}

	@PostMapping("/{postId}/hearts")
	@ResponseStatus(value = HttpStatus.CREATED)
	public String createPostLike(@PathVariable final Long postId, @RequestBody final HeartRequestDto requestDto) {
		postHeartService.create(postId, requestDto.getAccountId());
		return "좋아요를 눌렀습니다.";
	}

	@DeleteMapping("/{postId}/hearts")
	@ResponseStatus(value = HttpStatus.OK)
	public String deletePostLike(@PathVariable final Long postId, @RequestParam final Long accountId) {
		postHeartService.delete(postId, accountId);
		return "좋아요가 취소되었습니다.";
	}

}
