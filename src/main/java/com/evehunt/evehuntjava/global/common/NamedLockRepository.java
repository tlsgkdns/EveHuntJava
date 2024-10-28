package com.evehunt.evehuntjava.global.common;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NamedLockRepository {

    @Query(value = "select GET_LOCK(:key, :timeoutSeconds)", nativeQuery = true)
    Long getLock(String key, int timeoutSeconds);

    @Query(value = "select RELEASE_LOCK(:key)", nativeQuery = true)
    Long releaseLock(String key);

}
