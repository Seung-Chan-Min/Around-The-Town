package com.prgm.aroundthetown.member;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "wishlist")
public class Wishlist {

    @Id
    @Column(name = "wishlist_id")
    private Long wishlistId;

    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "product_id")
    private Long productId;

}
