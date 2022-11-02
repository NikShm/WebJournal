package com.webjournal.dto;


/*
@author Микола
@project WebJournal
@class FollowDTO
@version 1.0.0
@since 19.10.2022 - 13.01
*/
public class FollowDTO {
    private Integer userId;
    private Integer followingUserId;

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
        return "FollowDTO{" +
                "userId=" + userId +
                ", followingUserId=" + followingUserId +
                '}';
    }
}
