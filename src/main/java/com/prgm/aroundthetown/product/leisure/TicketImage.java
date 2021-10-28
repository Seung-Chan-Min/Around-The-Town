package com.prgm.aroundthetown.product.leisure;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ticket_image")
public class TicketImage {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "ticket_id")
    private Long ticketId;

    @Column(name = "IMAGE_PATH")
    private String IMAGE_PATH;

}
