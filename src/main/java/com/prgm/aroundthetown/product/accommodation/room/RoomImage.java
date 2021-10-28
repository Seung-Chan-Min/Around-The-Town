package com.prgm.aroundthetown.product.accommodation.room;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "room_image")
public class RoomImage {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "room_id")
    private Long roomId;

    @Column(name = "IMAGE_PATH")
    private String IMAGE_PATH;

}
