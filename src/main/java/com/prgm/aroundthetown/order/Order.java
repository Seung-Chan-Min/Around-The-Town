package com.prgm.aroundthetown.order;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "count")
    private int count;

}
