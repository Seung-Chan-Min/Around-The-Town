package com.prgm.aroundthetown.product.entity;

import com.prgm.aroundthetown.common.BaseEntity;
import com.prgm.aroundthetown.host.entity.Host;
import com.prgm.aroundthetown.member.entity.Cart;
import com.prgm.aroundthetown.member.entity.WishList;
import com.prgm.aroundthetown.order.entity.OrderProduct;
import com.prgm.aroundthetown.product.entity.vo.Location;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn
@Entity
public abstract class Product{
    @Id @GeneratedValue
    private Long id;

    // Todo : validation
    private String refundRule;
    private String phoneNumber;
    private String businessRegistrationNumber; // 사업자 등록번호
    private String businessAddress; // 사업자 주소
    private String businessName; // 상호명

    @Embedded
    private Location location; // Todo : Region (embedded)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "host_id")
    private Host host;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<Cart> carts;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<WishList> wishLists;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<OrderProduct> orderProducts;

    public void setHost(Host host) { // 연관관계 편의 메서드
        if (Objects.nonNull(this.host)) {
            this.host.getProducts().remove(this);
        }
        this.host = host;
        host.getProducts().add(this);
    }

    public void addCart(Cart cart) {
        cart.setProduct(this);
    }
    public void addWishList(WishList wishList) {
        wishList.setProduct(this);
    }
    public void addOrderProduct(OrderProduct orderProduct) {
        orderProduct.setProduct(this);
    }

}
