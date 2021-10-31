package com.prgm.aroundthetown.order.entity;

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
public class OrderProduct extends NecessaryBaseEntity {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    public void setOrder(Order order) { // 연관관계 편의 메서드
        if (Objects.nonNull(this.order)) {
            this.order.getOrderProducts().remove(this);
        }
        this.order = order;
        order.getOrderProducts().add(this);
    }

    public void setProduct(Product product) { // 연관관계 편의 메서드
        if (Objects.nonNull(this.product)) {
            this.product.getOrderProducts().remove(this);
        }
        this.product = product;
        product.getOrderProducts().add(this);
    }

}
