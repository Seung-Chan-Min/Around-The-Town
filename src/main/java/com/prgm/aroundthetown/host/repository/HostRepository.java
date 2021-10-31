package com.prgm.aroundthetown.host.repository;

import com.prgm.aroundthetown.host.entity.Host;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HostRepository extends JpaRepository<Host, Long> {
}
