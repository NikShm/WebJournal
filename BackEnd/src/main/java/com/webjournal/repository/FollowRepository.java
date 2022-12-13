package com.webjournal.repository;

import com.webjournal.entity.Follow;
import com.webjournal.entity.FollowId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Yuliana
 * @version 1.0.0
 * @project WebJournal
 * @class FollowRepository
 * @since 12/12/2022 - 20.19
 **/
@Repository
public interface FollowRepository extends JpaRepository<Follow, FollowId> {
    boolean existsByUserIdAndFollowingUserId(Integer userId, Integer followingUserId);
    int countByUserId(Integer userId);
    int countByFollowingUserId(Integer followingUserId);
}
