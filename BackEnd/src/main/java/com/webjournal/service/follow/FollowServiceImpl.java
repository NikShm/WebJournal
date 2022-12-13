package com.webjournal.service.follow;

import com.webjournal.entity.Follow;
import com.webjournal.entity.FollowId;
import com.webjournal.exception.DatabaseFetchException;
import com.webjournal.exception.InvalidRequestException;
import com.webjournal.repository.FollowRepository;
import com.webjournal.repository.UserRepository;
import org.springframework.stereotype.Service;

/**
 * @author Yuliana
 * @version 1.0.0
 * @project WebJournal
 * @class FollowServiceImpl
 * @since 12/12/2022 - 20.20
 **/
@Service
public class FollowServiceImpl implements FollowService {
    private final FollowRepository repository;
    private final UserRepository userRepository;

    public FollowServiceImpl(FollowRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    @Override
    public void follow(Integer userId, Integer userToFollowId) {
        if (userId.equals(userToFollowId)) {
            throw new InvalidRequestException("It is forbidden to follow yourself");
        }
        if (!userRepository.existsById(userToFollowId)) {
            throw new DatabaseFetchException("Could not find User entity with id " + userToFollowId);
        }
        repository.save(new Follow(userToFollowId, userId));
    }

    @Override
    public void unfollow(Integer userId, Integer userToUnfollowId) {
        if (!repository.existsByUserIdAndFollowingUserId(userToUnfollowId, userId)) {
            throw new DatabaseFetchException("Could not find Follow entity with id {" + userToUnfollowId + ", " + userId + "}");
        }
        repository.deleteById(new FollowId(userToUnfollowId, userId));
    }

    @Override
    public int getNumberOfUserFollowings(Integer userId) {
        return repository.countByFollowingUserId(userId);
    }

    @Override
    public boolean isFollowing(Integer userId, Integer followerId) {
        return repository.existsByUserIdAndFollowingUserId(userId, followerId);
    }
}
