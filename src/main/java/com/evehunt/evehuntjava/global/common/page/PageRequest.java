package com.evehunt.evehuntjava.global.common.page;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Pageable;

public record PageRequest (
    int page,
    int size,
    Boolean asc,
    String sortType,
    String keyword,
    String searchType
){
    @Override
    @NotNull
    public Boolean asc() {
        if(asc == null) return false;
        else return asc;
    }
    @Override
    @NotNull
    public String keyword() {
        if(keyword == null) return "";
        return keyword;
    }
    @Override
    @NotNull
    public String searchType() {
        if(searchType == null) return "";
        return searchType;
    }
    @Override
    @NotNull
    public String sortType() {
        if(sortType == null) return "";
        return sortType;
    }


    @NotNull
    public Pageable getPageable() {
        return org.springframework.data.domain.PageRequest.of(this.page - 1, this.size);
    }
}
