package com.evehunt.evehuntjava.domain.member.repository;

import com.evehunt.evehuntjava.domain.member.model.Member;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findMemberByEmail(String var1);

    boolean existsByEmail(@NotNull String var1);

    List<Member> getMembersBySuspendedTimeIsNotNull();
}