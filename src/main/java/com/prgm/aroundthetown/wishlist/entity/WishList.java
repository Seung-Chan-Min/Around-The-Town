package com.prgm.aroundthetown.wishlist.entity;

import com.prgm.aroundthetown.common.entity.BaseTimeAndDeletedEntity;
import com.prgm.aroundthetown.member.entity.Member;
import com.prgm.aroundthetown.product.Product;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "wishlist")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class WishList extends BaseTimeAndDeletedEntity {

    @Id
    @Column(name = "wishlist_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long wishlistId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", referencedColumnName = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id", referencedColumnName = "member_id", nullable = false)
    private Member member;

    @Builder
    public WishList(final Product product, final Member member) {
        this.product = product;
        this.member = member;
        product.addWishList(this);
        member.addWishList(this);
    }
}
