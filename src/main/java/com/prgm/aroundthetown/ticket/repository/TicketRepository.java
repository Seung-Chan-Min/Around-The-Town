package com.prgm.aroundthetown.ticket.repository;

import com.prgm.aroundthetown.leisure.entity.Leisure;
import com.prgm.aroundthetown.ticket.entity.Ticket;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> getAllByLeisure(Leisure leisure);
}
