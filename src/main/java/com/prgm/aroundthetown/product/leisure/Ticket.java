package com.prgm.aroundthetown.product.leisure;

import javax.persistence.Column;
import javax.persistence.Id;

public class Ticket {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "ticket_name")
    private String ticketName;

}
