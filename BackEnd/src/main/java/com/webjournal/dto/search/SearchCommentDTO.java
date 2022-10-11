package com.webjournal.dto.search;

import com.webjournal.enums.SortDirection;

/*
@author Микола
@project WebJournal
@class SearchPostDTO
@version 1.0.0
@since 14.09.2022 - 19.34
*/
public class SearchCommentDTO {
    private String sortField;
    private SortDirection sortDirection;
    private Integer page;
    private Integer pageSize;

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public SortDirection getSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(SortDirection sortDirection) {
        this.sortDirection = sortDirection;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public String toString() {
        return "SearchPostDTO{" +
                ", sortField='" + sortField + '\'' +
                ", sortDirection=" + sortDirection +
                ", page=" + page +
                ", pageSize=" + pageSize +
                '}';
    }
}
