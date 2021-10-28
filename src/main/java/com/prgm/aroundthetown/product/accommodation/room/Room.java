package com.prgm.aroundthetown.product.accommodation.room;

import com.prgm.aroundthetown.product.accommodation.Accommodation;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Room extends Accommodation {

    @Column
    private Long roomId;

    @Column(name = "room_name")
    private String roomName;

    @Column(name = "reservation_notice")
    private String reservationNotice;

    @Column(name = "room_information")
    private String roomInformation;

    @Column(name = "standard_people")
    private int standardPeople;

    @Column(name = "maximum_people")
    private int maximumPeople;

}
