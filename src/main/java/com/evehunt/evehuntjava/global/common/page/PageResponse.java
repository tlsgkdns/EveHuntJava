package com.evehunt.evehuntjava.global.common.page;

import jakarta.validation.constraints.NotNull;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(force = true)
public class PageResponse<T> {
    private final int page;
    private final int size;
    private final String sortType;
    private final String keyword;
    private final String searchType;
    private final Boolean asc;
    private final int total;
    private final int start;
    private final int end;
    private final boolean prev;
    private final boolean next;
    private final List<T> dtoList;


    public final int getPage() {
        return this.page;
    }

    public final int getSize() {
        return this.size;
    }

    public final String getSortType() {
        return this.sortType;
    }

    public final String getKeyword() {
        return this.keyword;
    }

    public final String getSearchType() {
        return this.searchType;
    }

    public final Boolean getAsc() {
        return this.asc;
    }

    public final int getTotal() {
        return this.total;
    }

    public final int getStart() {
        return this.start;
    }

    public final int getEnd() {
        return this.end;
    }

    public final boolean getPrev() {
        return this.prev;
    }

    public final boolean getNext() {
        return this.next;
    }

    public final List<T> getDtoList() {
        return this.dtoList;
    }

    public PageResponse(int page, int size, String sortType, String keyword, String searchType, Boolean asc, int total, int start, int end, boolean prev, boolean next, List<T> dtoList) {
        this.page = page;
        this.size = size;
        this.sortType = sortType;
        this.keyword = keyword;
        this.searchType = searchType;
        this.asc = asc;
        this.total = total;
        this.start = start;
        this.end = end;
        this.prev = prev;
        this.next = next;
        this.dtoList = dtoList;
    }
    @NotNull
    public static <T> PageResponse<T> of(@NotNull PageRequest pageRequestDTO, @NotNull List<T> dtoList, int total) {
        int page = pageRequestDTO.getPage();
        int size = pageRequestDTO.getSize();
        double last = Math.ceil( (double)total / (double)size);
        int end = (int)Math.min(Math.ceil((double)page / 10.0) * 10, last);
        int start = (int)(Math.ceil((double)page / 10.0) * (double)10) - 9;
        return new PageResponse<>(page, size, pageRequestDTO.getSortType(), pageRequestDTO.getKeyword(), pageRequestDTO.getSearchType(),
                pageRequestDTO.getAsc(), total, start, end, (start > 1), total > end * size, dtoList);
    }
}
