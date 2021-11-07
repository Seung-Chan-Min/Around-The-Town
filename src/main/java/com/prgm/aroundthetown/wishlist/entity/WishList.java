package com.prgm.aroundthetown.wishlist.entity;

import com.prgm.aroundthetown.annotation.SoftDeletableEntity;
import com.prgm.aroundthetown.common.entity.BaseTimeAndDeletedEntity;
import com.prgm.aroundthetown.member.entity.Member;
import com.prgm.aroundthetown.product.entity.Product;
import lombok.*;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;

@Entity
@Table(name = "wishlist")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SoftDeletableEntity
@SQLDelete(sql = "UPDATE wishList SET is_deleted = true WHERE wishList_id=?")
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
