package com.prgm.aroundthetown.member.repository;

import com.prgm.aroundthetown.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
