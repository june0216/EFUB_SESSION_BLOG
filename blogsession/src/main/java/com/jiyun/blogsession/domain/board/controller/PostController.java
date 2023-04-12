package com.jiyun.blogsession.domain.board.controller;


import com.jiyun.blogsession.domain.account.domain.Account;
import com.jiyun.blogsession.domain.account.service.AccountService;
import com.jiyun.blogsession.domain.board.domain.Post;
import com.jiyun.blogsession.domain.board.dto.request.AccountInfoRequestDto;
import com.jiyun.blogsession.domain.board.dto.response.PostListResponseDto;
import com.jiyun.blogsession.domain.board.dto.request.PostRequestDto;
import com.jiyun.blogsession.domain.board.dto.response.PostResponseDto;
import com.jiyun.blogsession.domain.board.service.PostHeartService;
import com.jiyun.blogsession.domain.board.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController//Rest 방식으로 개발을 한다.
@RequestMapping("/posts")//핸들러에서 해당 경로로 매핑된다.
@RequiredArgsConstructor//final이 붙거나 @NotNull 이 붙은 필드의 생성자를 자동 생성해주는 롬복 어노테이션
public class PostController {
	private final PostService postService;//서비스에서 트랜잭션을 관리해주기 때문에 서비스에서만 엔티티를 만들어야 한다.
	private final AccountService accountService;
	private final PostHeartService postHeartService;

	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public PostResponseDto createPost(@RequestBody @Valid final PostRequestDto requestDto) {
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
	public PostResponseDto readPost(@PathVariable Long postId, @RequestParam final Long accountId) {
		Post post = postService.findById(postId);
		Integer heartCount = postHeartService.countPostHeart(post);
		boolean isHeart = postHeartService.isHeart(accountId, post);
		PostResponseDto responseDto = PostResponseDto.of(post);
		responseDto.uploadHeart(heartCount, isHeart);
		return responseDto;
	}


	@PutMapping("/{postId}")
	@ResponseStatus(value = HttpStatus.OK)
	public PostResponseDto updatePost(@PathVariable final Long postId, @RequestBody final PostRequestDto requestDto) {
		postService.update(postId, requestDto);
		Post post = postService.findById(postId);
		Account account = accountService.findById(requestDto.getAccountId());
		Integer heartCount = postHeartService.countPostHeart(post);
		boolean isHeart = postHeartService.isExistsByWriterAndPost(account, post);
		PostResponseDto responseDto = PostResponseDto.of(post);
		responseDto.uploadHeart(heartCount, isHeart);
		return responseDto;
	}

	@DeleteMapping("/{postId}")
	@ResponseStatus(value = HttpStatus.OK)
	public String deletePost(@PathVariable final Long postId,  @RequestParam final Long accountId) {
		postService.delete(postId, accountId);
		return "성공적으로 삭제되었습니다.";
	}

	@PostMapping("/{postId}/hearts")
	@ResponseStatus(value = HttpStatus.CREATED)
	public String createPostLike(@PathVariable final Long postId, @RequestBody final AccountInfoRequestDto requestDto) {
		postHeartService.create(postId, requestDto);
		return "좋아요를 눌렀습니다.";
	}

	@DeleteMapping("{postId}/hearts")
	@ResponseStatus(value = HttpStatus.OK)
	public String deletePostLike(@PathVariable final Long postId ,@RequestParam final Long accountId) {
		postHeartService.delete(postId, accountId);
		return "좋아요가 취소되었습니다.";
	}

}
