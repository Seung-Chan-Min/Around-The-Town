package com.prgm.aroundthetown.member;

import lombok.Builder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "member")
@Builder
public class Member {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "password")
    private String password;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "cart_id")
    private Cart cart;

    @Column(name = "wishlist_id")
    private Wishlist wishlist;

    @Column(name = "review_list")
    private List<Review> review;

    protected Member() {

    }
}
