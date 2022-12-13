package com.webjournal.dto.search;

/**
 * @author Yuliana
 * @version 1.0.0
 * @project WebJournal
 * @class AuthorsPostsSearch
 * @since 12/4/2022 - 21.39
 **/
public class AuthorsPostsSearch {
    private Boolean areApproved;

    public AuthorsPostsSearch() {
    }

    public AuthorsPostsSearch(Boolean areApproved) {
        this.areApproved = areApproved;
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
                ", areApproved=" + areApproved +
                '}';
    }
}
