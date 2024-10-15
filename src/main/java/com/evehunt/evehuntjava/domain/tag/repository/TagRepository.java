package com.evehunt.evehuntjava.domain.tag.repository;

import com.evehunt.evehuntjava.domain.tag.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long>, QueryDslTagRepository {
}