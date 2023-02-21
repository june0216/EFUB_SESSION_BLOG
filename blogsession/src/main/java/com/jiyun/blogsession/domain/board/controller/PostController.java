package com.jiyun.blogsession.domain.board.controller;


import com.jiyun.blogsession.domain.board.domain.Post;
import com.jiyun.blogsession.domain.board.dto.PostListResponseDto;
import com.jiyun.blogsession.domain.board.dto.PostRequestDto;
import com.jiyun.blogsession.domain.board.dto.PostResponseDto;
import com.jiyun.blogsession.domain.board.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

	@GetMapping("/{accountId}")
	@ResponseStatus(value = HttpStatus.OK)// 글 1개 조회
	public PostResponseDto readBoard(@PathVariable Long postId) {
		Post post = postService.findById(postId);
		return PostResponseDto.of(post);
	}

	@GetMapping("/{accountId}")//작성자 별 조회
	@ResponseStatus(value = HttpStatus.OK)
	public PostListResponseDto readBoardList(@PathVariable Long accountId) {
		List<Post> postList = postService.findByWriter(accountId);
		return PostListResponseDto.of(postList);
	}



	@PutMapping("/{postId}")
	@ResponseStatus(value = HttpStatus.OK)
	public PostResponseDto update(@PathVariable final Long postId, @RequestBody final PostRequestDto requestDto) {
		postService.update(postId, requestDto);
		Post post = postService.findById(postId);

/*		Integer likeCount = postLikeService.countPostLike(post);
		boolean isLiked = postLikeService.isExistsByWriterAndPost(account, post);

		BoardResponseDto responseDto = BoardResponseDto.of(board);

		BoardresponseDto.setLike(likeCount, isLiked);*/

		return PostResponseDto.of(post);
	}



	@DeleteMapping("/{postId}")
	@ResponseStatus(value = HttpStatus.OK)
	public void delete(@PathVariable final Long boardId) {
		postService.delete(boardId);
	}

}
