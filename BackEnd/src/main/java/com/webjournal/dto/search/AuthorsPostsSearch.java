package com.webjournal.dto.search;

/**
 * @author Yuliana
 * @version 1.0.0
 * @project WebJournal
 * @class AuthorsPostsSearch
 * @since 12/4/2022 - 21.39
 **/
public class AuthorsPostsSearch {
    private Integer authorId;
    private Boolean areApproved;

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public Boolean getAreApproved() {
        return areApproved;
    }

    public void setAreApproved(Boolean areApproved) {
        this.areApproved = areApproved;
    }

    @Override
    public String toString() {
        return "AuthorsPostsSearch{" +
                "authorId=" + authorId +
                ", areApproved=" + areApproved +
                '}';
    }
}
