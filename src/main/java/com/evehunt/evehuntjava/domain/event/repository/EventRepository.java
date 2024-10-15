package com.evehunt.evehuntjava.domain.event.repository;

import com.evehunt.evehuntjava.domain.event.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long>, QueryDslEventRepository {
}