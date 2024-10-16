package com.evehunt.evehuntjava.global.common.page;

import jakarta.validation.constraints.NotNull;
import lombok.NoArgsConstructor;
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
    public Boolean asc() {
        if(asc == null) return false;
        else return asc;
    }

    @NotNull
    public Pageable getPageable() {
        return org.springframework.data.domain.PageRequest.of(this.page - 1, this.size);
    }
}
