package com.prgm.aroundthetown.ticket.repository;

import com.prgm.aroundthetown.ticket.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
