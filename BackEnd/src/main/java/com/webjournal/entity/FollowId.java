package com.webjournal.entity;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author Yuliana
 * @version 1.0.0
 * @project WebJournal
 * @class FollowId
 * @since 12/12/2022 - 20.07
 **/
public class FollowId implements Serializable {
    private Integer userId;
    private Integer followingUserId;

    public FollowId() {
    }

    public FollowId(Integer userId, Integer followingUserId) {
        this.userId = userId;
        this.followingUserId = followingUserId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FollowId followId = (FollowId) o;
        return userId.equals(followId.userId) && followingUserId.equals(followId.followingUserId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, followingUserId);
    }
}
