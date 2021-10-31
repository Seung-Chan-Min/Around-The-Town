package com.prgm.aroundthetown.host.entity;

import com.prgm.aroundthetown.common.BaseEntity;
import com.prgm.aroundthetown.product.entity.Product;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
public class Host extends BaseEntity{
    @Id @GeneratedValue
    private Long id;

    // Todo : validation
    private String name;
    private String password; // Note : add
    private String email;
    private String phoneNumber;

    @OneToMany(mappedBy = "host", fetch = FetchType.LAZY)
    @Builder.Default
    private List<Product> products = new ArrayList<>();

    public void addProduct(Product product) {
        product.setHost(this);
    }

}


