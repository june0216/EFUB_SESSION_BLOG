package com.jiyun.blogsession.domain.account.controller;

import com.jiyun.blogsession.domain.account.domain.Account;
import com.jiyun.blogsession.domain.account.domain.Follow;
import com.jiyun.blogsession.domain.account.dto.FollowStatusResponseDto;
import com.jiyun.blogsession.domain.account.dto.FollowListResponseDto;
import com.jiyun.blogsession.domain.account.dto.FollowRequestDto;
import com.jiyun.blogsession.domain.account.service.AccountService;
import com.jiyun.blogsession.domain.account.service.FollowService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/follows")
@RequiredArgsConstructor
public class FollowController {

	private final FollowService followService;
	private final AccountService accountService;


	@GetMapping("/{accountId}")
	@ResponseStatus(value = HttpStatus.OK)//팔로우, 팔로워 리스트
	public FollowListResponseDto getFollowList(@PathVariable Long accountId)
	{
		List<Follow> followerList = followService.findAllByFollowerId(accountId);
		List<Follow> followingList = followService.findAllByFollowingId(accountId);
		return FollowListResponseDto.of(followerList, followingList);
	}

	@GetMapping("/{accountId}/search")
	@ResponseStatus(value = HttpStatus.OK)//이메일 유저 검색
	public FollowStatusResponseDto searchAccount(@PathVariable Long accountId, @RequestParam String email){
		Account searchAccount = accountService.findByEmail(email);
		Boolean isFollow = followService.isFollowing(accountId, searchAccount.getAccountId());
		return new FollowStatusResponseDto(searchAccount, isFollow);
	}


	@PostMapping("/{accountId}")
	@ResponseStatus(value = HttpStatus.CREATED)//팔로우 - 상태 하기 
	public FollowStatusResponseDto addFollow(@PathVariable Long accountId, @RequestBody final FollowRequestDto requestDto) {
		Long id = followService.add(accountId, requestDto);
		Boolean isFollow = followService.isFollowing(accountId, requestDto.getFollowingId());
		Account findAccount = accountService.findById(requestDto.getFollowingId());
		return new FollowStatusResponseDto(findAccount, isFollow);
	}

	//언팔로우
	@DeleteMapping("/{accountId}")
	@ResponseStatus(value = HttpStatus.OK)
	public FollowStatusResponseDto deleteFollow(@PathVariable Long accountId, @RequestBody final FollowRequestDto requestDto)//상태를 넘겨주기
	{
		followService.delete(accountId, requestDto);
		Account findAccount = accountService.findById(requestDto.getFollowingId());
		Boolean isFollow = followService.isFollowing(accountId, requestDto.getFollowingId());
		return new FollowStatusResponseDto(findAccount, isFollow);
	}
}
