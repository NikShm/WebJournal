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
    @ApiModelProperty(value = "The total number of elements in the database")
    Long totalItem;

    public Long getTotalItem() {
        return totalItem;
    }

    public void setTotalItem(Long totalItem) {
        this.totalItem = totalItem;
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }
}
