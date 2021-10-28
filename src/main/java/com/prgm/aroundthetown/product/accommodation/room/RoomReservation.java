package com.prgm.aroundthetown.product.accommodation.room;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "room_reservation")
public class RoomReservation {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "room_id")
    private Long roomId;

    @Column(name = "remains")
    private int remains;

    @Column(name = "dates")
    private LocalDateTime dates;

}
