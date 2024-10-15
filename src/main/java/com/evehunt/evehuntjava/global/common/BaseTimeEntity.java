package com.evehunt.evehuntjava.global.common;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.ZonedDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
abstract public class BaseTimeEntity {
    @CreationTimestamp
    @Column(nullable = false)
    ZonedDateTime createdAt = ZonedDateTime.now();
    @UpdateTimestamp
    @Column(nullable = false)
    ZonedDateTime updatedAt = ZonedDateTime.now();

    @NotNull
    public ZonedDateTime getCreatedAt() {
        return this.createdAt;
    }
    @NotNull
    public ZonedDateTime getUpdatedAt() {
        return this.updatedAt;
    }
}
