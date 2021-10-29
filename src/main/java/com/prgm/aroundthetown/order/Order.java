package com.prgm.aroundthetown.order;

import com.prgm.aroundthetown.common.BaseTimeAndDeletedEntity;
import com.prgm.aroundthetown.member.Member;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Order extends BaseTimeAndDeletedEntity {

    @Id
    @Column(name = "order_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "count")
    private int count;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id", referencedColumnName = "member_id", nullable = false)
    private Member member;

    @OneToMany(mappedBy = "orders", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderProduct> orderProducts = new ArrayList<>();

    @Builder
    public Order(final int count, final Member member) {
        this.count = count;
        this.member = member;
        member.addOrder(this);
    }

    public void addOrderProduct(final OrderProduct orderProduct) {
        if (Objects.isNull(orderProducts)) {
            orderProducts = new ArrayList<>();
        }
        orderProducts.add(orderProduct);
    }
}
