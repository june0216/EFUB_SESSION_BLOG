package com.jiyun.blogsession.domain.account.repository;

import com.jiyun.blogsession.domain.account.domain.Account;
import com.jiyun.blogsession.domain.account.domain.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.StyledEditorKit;
import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Long> {
	Follow findByFollowerAndFollowing(Account follower, Account following);
	void deleteByFollowId(Long followId);

	List<Follow> findAllByFollower(Account follower);
	List<Follow> findAllByFollowing(Account following);

	Boolean existsByFollowerAndFollowing(Account follower, Account following);
}
