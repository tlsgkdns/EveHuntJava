package com.evehunt.evehuntjava.domain.event.repository;

import com.evehunt.evehuntjava.domain.event.dto.EventCardResponse;
import com.evehunt.evehuntjava.domain.event.dto.EventIdResponse;
import com.evehunt.evehuntjava.global.common.page.PageRequest;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;

import java.util.List;

public interface QueryDslEventRepository {
    @NotNull
    Page<EventCardResponse> searchEvents(@NotNull PageRequest var1);

    @NotNull
    List<EventIdResponse> setExpiredEventsClosed();

    @NotNull
    List<EventCardResponse> getPopularEvents();

}
