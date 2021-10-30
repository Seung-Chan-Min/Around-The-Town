package com.prgm.aroundthetown.product.ticket.repository;

import com.prgm.aroundthetown.product.ticket.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
