package com.prgm.aroundthetown.member;

import com.prgm.aroundthetown.common.BaseTimeAndDeletedEntity;
import com.prgm.aroundthetown.order.Order;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "member")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Member extends BaseTimeAndDeletedEntity {

    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cart> carts = new ArrayList<>();

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WishList> wishLists = new ArrayList<>();

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();

    public void addCart(final Cart cart) {
        if (Objects.isNull(carts)) {
            carts = new ArrayList<>();
        }
        carts.add(cart);
    }

    public void addWishList(final WishList wishList) {
        if (Objects.isNull(wishLists)) {
            wishLists = new ArrayList<>();
        }
        wishLists.add(wishList);
    }

    public void addReview(final Review review) {
        if (Objects.isNull(reviews)) {
            reviews = new ArrayList<>();
        }
        reviews.add(review);
    }

    public void addOrder(final Order order) {
        if (Objects.isNull(orders)) {
            orders = new ArrayList<>();
        }
        orders.add(order);
    }
}
