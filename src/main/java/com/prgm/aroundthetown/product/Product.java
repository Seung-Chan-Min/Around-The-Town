package com.prgm.aroundthetown.product;

import javax.persistence.*;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @Column(name = "productId")
    private Long productId;

    @Column(name = "hostId")
    private Long hostId;

    @Column(name = "refundRule")
    private String refundRule;

    @Column(name = "location")
    @Embedded
    private Location location;

    @Column(name = "price")
    private int price;

    @Column(name = "phoneNumber")
    private String phoneNumber;

    @Column(name = "businessRegistrationNumber")
    private String businessRegistrationNumber;

    @Column(name = "businessAddress")
    private String businessAddress;

    @Column(name = "businessName")
    private String businessName;

    @Column(name = "region")
    private Region region;

}
