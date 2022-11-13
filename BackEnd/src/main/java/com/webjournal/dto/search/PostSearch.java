package com.webjournal.dto.search;

/*
@author Микола
@project WebJournal
@class PostSearch
@version 1.0.0
@since 14.09.2022 - 19.34
*/
public class PostSearch {
    private String search;
    private String searchTag;

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getSearchTag() {
        return searchTag;
    }

    public void setSearchTag(String searchTag) {
        this.searchTag = searchTag;
    }

    @Override
    public String toString() {
        return "PostSearch{" +
                "search='" + search + '\'' +
                ", searchTag='" + searchTag + '\'' +
                '}';
    }
}
