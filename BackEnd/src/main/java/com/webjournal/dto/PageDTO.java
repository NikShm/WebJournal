package com.webjournal.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/*
@author Микола
@project WebJournal
@class PageDTO
@version 1.0.0
@since 14.09.2022 - 19.28
*/
@ApiModel(description = "Page creation options class")
public class PageDTO<T> {
    @ApiModelProperty(value = "List of elements of a given page")
    List<T> content;
    @ApiModelProperty(value = "Total number of pages")
    int pageCount;
    @ApiModelProperty(value = "Page number to be returned")
    int currentPage;
    @ApiModelProperty(value = "The size of the returned page")
    int pageSize;
    @ApiModelProperty(value = "The total number of elements in the database")
    long totalItem;

    public long getTotalItem() {
        return totalItem;
    }

    public void setTotalItem(long totalItem) {
        this.totalItem = totalItem;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
}
