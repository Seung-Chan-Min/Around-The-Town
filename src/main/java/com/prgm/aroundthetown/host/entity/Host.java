package com.prgm.aroundthetown.host.entity;

import com.prgm.aroundthetown.common.entity.BaseTimeAndDeletedEntity;
import com.prgm.aroundthetown.product.entity.Product;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "host")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Host extends BaseTimeAndDeletedEntity {

    @Id
    @Column(name = "host_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "host_name", nullable = false)
    private String hostName;

    @Column(name = "host_email", nullable = false)
    private String hostEmail;

    @Column(name = "host_phone_number", length = 11, nullable = false)
    private String hostPhoneNumber;

    @OneToMany(mappedBy = "host", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Product> products = new ArrayList<>();

    @Builder
    public Host(final Long id, final String hostName, final String hostEmail, final String hostPhoneNumber) {
        this.id = id;
        this.hostName = hostName;
        this.hostEmail = hostEmail;
        this.hostPhoneNumber = hostPhoneNumber;
    }

    public void addProduct(final Product product) {
        if (Objects.isNull(products)) {
            products = new ArrayList<>();
        }
        products.add(product);
    }

    public void update(final String name, final String email, final String phoneNumber) {
        this.hostName = name;
        this.hostEmail = email;
        this.hostPhoneNumber = phoneNumber;
    }

}
