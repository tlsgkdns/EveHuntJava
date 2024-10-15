package com.evehunt.evehuntjava.global.infra.cache;

import jakarta.validation.constraints.NotNull;

public enum CacheType {
    POPULAR_TAGS("popularTags", 2000, 1000),
    POPULAR_EVENTS("popularEvents", 2000, 10000),
    EVENTS_LIST("eventList", 30, 10000),
    EVENT_TAGS("eventTags", 30, 10000);

    @NotNull
    private final String cacheName;
    private final long expireTime;
    private final long maxSize;

    @NotNull
    public final String getCacheName() {
        return this.cacheName;
    }

    public final long getExpireTime() {
        return this.expireTime;
    }

    public final long getMaxSize() {
        return this.maxSize;
    }

    private CacheType(String cacheName, long expireTime, long maxSize) {
        this.cacheName = cacheName;
        this.expireTime = expireTime;
        this.maxSize = maxSize;
    }
}
