package com.prgm.aroundthetown.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.prgm.aroundthetown.cart.entity.Cart;
import com.prgm.aroundthetown.common.entity.BaseEntity;
import com.prgm.aroundthetown.host.entity.Host;
import com.prgm.aroundthetown.order.entity.OrderProduct;
import com.prgm.aroundthetown.wishlist.entity.WishList;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "product")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "product_type")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
public class Product extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "host_id", referencedColumnName = "host_id", nullable = false)
    protected Host host;

    @Id
    @Column(name = "product_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long productId;

    @Column(name = "refund_rule")
    @Lob
    private String refundRule;

    @Embedded
    private Location location;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "business_registration_number")
    private String businessRegistrationNumber;

    @Column(name = "business_address")
    private String businessAddress;

    @Column(name = "business_name")
    private String businessName;

    @Column(name = "region")
    @Enumerated(value = EnumType.STRING)
//    @Convert(converter = RegionConverter.class)
    private Region region;

    @JsonIgnore
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cart> carts = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WishList> wishLists = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderProduct> orderProducts = new ArrayList<>();

    public void addCart(final Cart cart) {
        if (Objects.isNull(carts)) {
            carts = new ArrayList<>();
        }
        carts.add(cart);
    }

    public void addWishList(final WishList wishList) {
        if (Objects.isNull(wishList)) {
            wishLists = new ArrayList<>();
        }
        wishLists.add(wishList);
    }

    public void addOrderProduct(final OrderProduct orderProduct) {
        if (Objects.isNull(orderProducts)) {
            orderProducts = new ArrayList<>();
        }
        orderProducts.add(orderProduct);
    }

}
