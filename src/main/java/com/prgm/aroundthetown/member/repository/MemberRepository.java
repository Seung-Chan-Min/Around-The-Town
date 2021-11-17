package com.prgm.aroundthetown.member.repository;

import com.prgm.aroundthetown.member.entity.Member;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<List<Member>> findAllByEmail(String Email);
    Optional<List<Member>> findAllByPhoneNumber(String phoneNumber);
}
