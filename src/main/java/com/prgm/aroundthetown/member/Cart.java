package com.prgm.aroundthetown.member;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cart")
public class Cart {

    @Id
    @Column(name = "cart_id")
    private Long cartId;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "member_id")
    private Long memberId;

}
