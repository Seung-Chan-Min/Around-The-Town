package com.prgm.aroundthetown.member.entity;

import com.prgm.aroundthetown.common.NecessaryBaseEntity;
import com.prgm.aroundthetown.product.entity.Product;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
public class WishList extends NecessaryBaseEntity {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    public void setMember(Member member) { // 연관관계 편의 메서드
        if (Objects.nonNull(this.member)) {
            this.member.getWishLists().remove(this);
        }
        this.member = member;
        member.getWishLists().add(this);
    }

    public void setProduct(Product product) { // 연관관계 편의 메서드
        if (Objects.nonNull(this.product)) {
            this.product.getWishLists().remove(this);
        }
        this.product = product;
        product.getWishLists().add(this);
    }

}
