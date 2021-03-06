package com.prgm.aroundthetown.product.entity;

import com.prgm.aroundthetown.annotation.SoftDeletableEntity;
import com.prgm.aroundthetown.cart.entity.Cart;
import com.prgm.aroundthetown.common.entity.BaseEntity;
import com.prgm.aroundthetown.host.entity.Host;
import com.prgm.aroundthetown.order.entity.OrderProduct;
import com.prgm.aroundthetown.product.Location;
import com.prgm.aroundthetown.product.Region;
import com.prgm.aroundthetown.wishlist.entity.WishList;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.hibernate.annotations.SQLDelete;

@Table(name = "product")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue(value = "DTYPE")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@Entity
@SoftDeletableEntity
@SQLDelete(sql = "UPDATE product SET is_deleted = true WHERE product_id=?")
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
    private Region region;

    @Column(name = "DTYPE", insertable = false, updatable = false)
    @Enumerated(value = EnumType.STRING)
    private ProductType productType;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Cart> carts = new ArrayList<>();

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<WishList> wishLists = new ArrayList<>();

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<OrderProduct> orderProducts = new ArrayList<>();
    @Column(name = "is_deleted", columnDefinition = "boolean default false")
    private Boolean isDeleted = false;

    public void addCart(final Cart cart) {
        if (Objects.isNull(carts)) {
            carts = new ArrayList<>();
        }
        carts.add(cart);
    }

    public void addWishList(final WishList wishList) {
        if (Objects.isNull(wishLists)) {
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

    public void update(final String refundRule,
                       final String phoneNumber,
                       final String businessRegistrationNumber,
                       final String businessAddress,
                       final String businessName,
                       final Region region,
                       final Location location) {

        this.refundRule = refundRule;
        this.phoneNumber = phoneNumber;
        this.businessRegistrationNumber = businessRegistrationNumber;
        this.businessAddress = businessAddress;
        this.businessName = businessName;
        this.region = region;
        this.location = location;
    }
}
