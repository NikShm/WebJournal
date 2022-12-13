package com.webjournal.entity;

import javax.persistence.*;

/**
 * @author Yuliana
 * @version 1.0.0
 * @project WebJournal
 * @class Follow
 * @since 12/12/2022 - 20.00
 **/
@Entity
@Table(name = "follow")
@IdClass(FollowId.class)
public class Follow {
    @Id
    private Integer userId;

    @Id
    private Integer followingUserId;

    public Follow() {
    }

    public Follow(Integer userId, Integer followingUserId) {
        this.userId = userId;
        this.followingUserId = followingUserId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getFollowingUserId() {
        return followingUserId;
    }

    public void setFollowingUserId(Integer followingUserId) {
        this.followingUserId = followingUserId;
    }

    @Override
    public String toString() {
        return "Follow{" +
                "userId=" + userId +
                ", followingUserId=" + followingUserId +
                '}';
    }
}
