package com.webjournal.dto.search;


/*
@author Микола
@project WebJournal
@class CommentSearch
@version 1.0.0
@since 06.12.2022 - 22.43
*/
public class CommentSearch {
    private String postId;

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    @Override
    public String toString() {
        return "CommentSearch{" +
                "authorId=" + postId +
                '}';
    }
}
