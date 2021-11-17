package com.prgm.aroundthetown.order.entity;

import com.prgm.aroundthetown.annotation.SoftDeletableEntity;
import com.prgm.aroundthetown.common.entity.BaseTimeAndDeletedEntity;
import com.prgm.aroundthetown.product.entity.Product;
import lombok.*;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;

@Entity
@Table(name = "order_product")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SoftDeletableEntity
@SQLDelete(sql = "UPDATE order_product SET is_deleted = true WHERE order_product_id=?")
public class OrderProduct extends BaseTimeAndDeletedEntity {

    @Id
    @Column(name = "order_product_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", referencedColumnName = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", referencedColumnName = "product_id", nullable = false)
    private Product product;

    @Column(name = "count")
    private int count;

    @Builder
    public OrderProduct(final Order order, final Product product, final int count) {
        this.order = order;
        this.product = product;
        this.count = count;
        order.addOrderProduct(this);
        product.addOrderProduct(this);
    }

}
