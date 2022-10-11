package com.webjournal.dto;


import java.util.List;

/*
@author Микола
@project WebJournal
@class PageDTO
@version 1.0.0
@since 14.09.2022 - 19.28
*/
public class PageDTO<T> {
    List<T> content;
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
