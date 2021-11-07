package com.prgm.aroundthetown.leisure.repository;

import com.prgm.aroundthetown.host.entity.Host;
import com.prgm.aroundthetown.leisure.entity.Leisure;
import com.prgm.aroundthetown.leisure.entity.LeisureCategory;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeisureRepository extends JpaRepository<Leisure, Long> {
    List<Leisure> getAllByLeisureCategory(LeisureCategory leisureCategory);
    List<Leisure> getAllByHost(Host host);
}
