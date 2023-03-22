package com.jiyun.blogsession.domain.account.service;

import com.jiyun.blogsession.domain.account.domain.Account;
import com.jiyun.blogsession.domain.account.domain.Follow;
import com.jiyun.blogsession.domain.account.dto.FollowRequestDto;
import com.jiyun.blogsession.domain.account.repository.FollowRepository;
import com.jiyun.blogsession.domain.board.domain.Post;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Slf4j
@Service//서비스 레이어, 내부에서 자바 로직을 처리함
@Transactional
@RequiredArgsConstructor //final 키워드가 붙은 필드에 대해 생성자 자동 생성
public class FollowService {
	private final FollowRepository followRepository;
	public final AccountService accountService;
	public Account searchAccount(String email)
	{
		Account account = accountService.findByEmail(email);
		return account;
	}

	public Long add(Long accountId, FollowRequestDto followRequestDto){
		Account follower = accountService.findById(accountId);
		Account following = accountService.findById(followRequestDto.getFollowingId());
		Follow follow = followRepository.save(followRequestDto.toEntity(follower, following));
		return follow.getFollowId();
	}

	public void delete(Long accountId,FollowRequestDto followRequestDto) {
		Follow findFollow = findByFollowerIdAndFollowingId(accountId, followRequestDto.getFollowingId());
		followRepository.deleteByFollowId(findFollow.getFollowId());
	}

	@Transactional(readOnly = true)
	public boolean isFollowing(Long followerId, Long followingId){
		Account follower =accountService.findById(followerId);
		Account following =accountService.findById(followingId);
		return followRepository.existsByFollowerAndFollowing(follower, following);
	}


	@Transactional(readOnly = true)
	public Follow findByFollowerIdAndFollowingId(Long followerId, Long followingId) {
		Account follower =accountService.findById(followerId);
		Account following =accountService.findById(followingId);
		return followRepository.findByFollowerAndFollowing(follower, following);
	}

	@Transactional(readOnly = true)
	public Follow findById(Long id){
		return followRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("해당 id 를 가진 Account 를 찾을 수 없습니다. id ="+id));
	}

	@Transactional(readOnly = true)
	public List<Follow> findAllByFollowerId(Long accountId) {
		Account findAccount =accountService.findById(accountId);
		return followRepository.findAllByFollower(findAccount);
	}

	@Transactional(readOnly = true)
	public List<Follow> findAllByFollowingId(Long accountId) {
		Account findAccount =accountService.findById(accountId);
		return followRepository.findAllByFollowing(findAccount);
	}


}
