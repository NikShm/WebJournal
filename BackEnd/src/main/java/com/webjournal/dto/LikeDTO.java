package com.webjournal.dto;


/*
@author Микола
@project WebJournal
@class LikeDTO
@version 1.0.0
@since 18.10.2022 - 21.37
*/
public class LikeDTO {
    private Integer userId;
    private Integer postId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    @Override
    public String toString() {
        return "LikeDTO{" +
                "userId=" + userId +
                ", postId=" + postId +
                '}';
    }
}
