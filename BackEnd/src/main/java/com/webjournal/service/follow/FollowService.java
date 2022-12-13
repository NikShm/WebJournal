package com.webjournal.service.follow;

/**
 * @author Yuliana
 * @version 1.0.0
 * @project WebJournal
 * @class FollowService
 * @since 12/12/2022 - 20.20
 **/
public interface FollowService {
    void follow(Integer userId, Integer userToFollowId);
    void unfollow(Integer userId, Integer userToUnfollowId);
    int getNumberOfUserFollowings(Integer userId);
    boolean isFollowing(Integer followerId, Integer userId);
}
