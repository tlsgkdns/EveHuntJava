package com.evehunt.evehuntjava.global.common.page;

import jakarta.validation.constraints.NotNull;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;

@NoArgsConstructor(force = true)
public class PageRequest {
    private int page = 1;
    private int size = 10;
    private String link;
    private boolean asc = false;
    @NotNull
    private String sortType = "";
    @NotNull
    private String keyword = "";
    @NotNull
    private String searchType = "";

    @NotNull
    public final Pageable getPageable() {
        return org.springframework.data.domain.PageRequest.of(this.page - 1, this.size);
    }

    public final int getPage() {
        return this.page;
    }

    public final int getSize() {
        return this.size;
    }


    public final String getLink() {
        return this.link;
    }

    public final boolean getAsc() {
        return this.asc;
    }

    @NotNull
    public final String getSortType() {
        return this.sortType;
    }

    @NotNull
    public final String getKeyword() {
        return this.keyword;
    }

    @NotNull
    public final String getSearchType() {
        return this.searchType;
    }

    public PageRequest(int page, int size, String link, boolean asc, @NotNull String sortType, @NotNull String keyword, @NotNull String searchType) {
        this.page = page;
        this.size = size;
        this.link = link;
        this.asc = asc;
        this.sortType = sortType;
        this.keyword = keyword;
        this.searchType = searchType;
    }
}
