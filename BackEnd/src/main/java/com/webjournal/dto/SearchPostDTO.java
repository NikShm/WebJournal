package com.webjournal.dto;

import com.webjournal.enums.SortDirection;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/*
@author Микола
@project WebJournal
@class SearchPostDTO
@version 1.0.0
@since 14.09.2022 - 19.34
*/
@ApiModel(description = "Page creation options class")
public class SearchPostDTO {
    @ApiModelProperty(value = "Phrase entered by user in search field to find things of interest")
    private String search;
    private String searchTag;
    @ApiModelProperty(value = "The field by which sorting is performed")
    private String sortField;
    @ApiModelProperty(value = "Sorting type")
    private SortDirection sortDirection;
    @ApiModelProperty(value = "Page number to be returned")
    private Integer page;
    @ApiModelProperty(value = "The size of the returned page")
    private Integer pageSize;

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
                "search='" + search + '\'' +
                ", searchTeg='" + searchTag + '\'' +
                ", sortField='" + sortField + '\'' +
                ", sortDirection=" + sortDirection +
                ", page=" + page +
                ", pageSize=" + pageSize +
                '}';
    }
}
