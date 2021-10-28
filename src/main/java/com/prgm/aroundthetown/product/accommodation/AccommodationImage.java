package com.prgm.aroundthetown.product.accommodation;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "accommodation_image")
public class AccommodationImage {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "room_id")
    private Long accommodationId;

    @Column(name = "IMAGE_PATH")
    private String IMAGE_PATH;

}
