package com.webjournal.dto.search;

import com.webjournal.enums.SortDirection;

/*
@author Микола
@project WebJournal
@class PostSearch
@version 1.0.0
@since 14.09.2022 - 19.34
*/
public class AuthorSearch {
    private String search;

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    @Override
    public String toString() {
        return "AuthorSearch{" +
                "search='" + search + '\'' +
                '}';
    }
}
