package com.prgm.aroundthetown.host;

import com.prgm.aroundthetown.common.BaseTimeAndDeletedEntity;
import com.prgm.aroundthetown.product.Product;
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
    public Host(final String hostName, final String hostEmail, final String hostPhoneNumber) {
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

    public Host changeName(final String name) {
        this.hostName = name;
        return this;
    }

    public Host changeEmail(final String email) {
        this.hostEmail = email;
        return this;
    }

    public Host changePhoneNumber(final String phoneNumber) {
        this.hostPhoneNumber = phoneNumber;
        return this;
    }

}
