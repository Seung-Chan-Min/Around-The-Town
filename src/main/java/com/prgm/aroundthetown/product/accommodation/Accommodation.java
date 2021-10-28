package com.prgm.aroundthetown.product.accommodation;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "accommodation")
public class Accommodation {

    @Id
    @Column(name = "accommodation_name")
    private String accommodationName;

    @Column(name = "accommodation_notice")
    private String accommodationNotice;

    @Column(name = "facilities")
    private List<Option> facilities = new ArrayList<>();

    @Column(name = "guide")
    private String guide;

    @Column(name = "category")
    private Category category;

}
