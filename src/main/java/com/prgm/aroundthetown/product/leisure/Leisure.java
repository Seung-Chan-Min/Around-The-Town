package com.prgm.aroundthetown.product.leisure;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "leisure")
public class Leisure {

    @Id
    @Column(name = "id")
    Long id;

    @Column(name = "ticket_id")
    Long ticketId;

    @Column(name = "leisure_infomation")
    String leisureInfomation;

    @Column(name = "usecase")
    String usecase;

    @Column(name = "leisure_notice")
    String leisureNotice;

    @Column(name = "expiration_date")
    LocalDateTime expirationDate;

}
