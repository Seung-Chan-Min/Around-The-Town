package com.prgm.aroundthetown.member.entity;

import com.prgm.aroundthetown.common.BaseEntity;
import com.prgm.aroundthetown.common.NecessaryBaseEntity;
import com.prgm.aroundthetown.order.entity.Order;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member extends BaseEntity {

    @Id @GeneratedValue
    private Long id;

    // Todo : validation
    private String name;
    private String password;
    private String email;
    private String phoneNumber;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    @Builder.Default
    private List<Cart> carts = new ArrayList<>();

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    @Builder.Default
    private List<WishList> wishLists = new ArrayList<>();

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    @Builder.Default
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    @Builder.Default
    private List<Order> orders = new ArrayList<>();

    public void addCart(Cart cart) {
        cart.setMember(this);
    }

    public void addWishList(WishList wishList) {
        wishList.setMember(this);
    }

    public void addReview(Review review) {
        review.setMember(this);
    }

    public void addOrder(Order order) {
        order.setMember(this);
    }

}
