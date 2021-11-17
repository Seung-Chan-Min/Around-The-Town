package com.prgm.aroundthetown.cart.entity;

import com.prgm.aroundthetown.annotation.SoftDeletableEntity;
import com.prgm.aroundthetown.common.entity.BaseTimeAndDeletedEntity;
import com.prgm.aroundthetown.member.entity.Member;
import com.prgm.aroundthetown.product.entity.Product;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;

@Entity
@Table(name = "cart")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SoftDeletableEntity
@SQLDelete(sql = "UPDATE cart SET is_deleted = true WHERE cart_id=?")
public class Cart extends BaseTimeAndDeletedEntity {

    @Id
    @Column(name = "cart_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long cartId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", referencedColumnName = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id", referencedColumnName = "member_id", nullable = false)
    private Member member;

    @Column(name = "count")
    private int count;

    @Builder
    public Cart(final Long cartId, final Product product, final Member member, final int count) {
        this.cartId = cartId;
        this.product = product;
        this.member = member;
        this.count = count;
        product.addCart(this);
        member.addCart(this);
    }
}
