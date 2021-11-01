package com.prgm.aroundthetown.member.dto;

import com.prgm.aroundthetown.cart.entity.Cart;
import com.prgm.aroundthetown.order.entity.Order;
import com.prgm.aroundthetown.review.entity.Review;
import com.prgm.aroundthetown.wishlist.entity.WishList;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
public class MemberDto {
    private Long id;
    private String password;
    private String phoneNumber;
    private String email;
    private List<Cart> carts;
    private List<WishList> wishLists;
    private List<Review> reviews;
    private List<Order> orders;
}
