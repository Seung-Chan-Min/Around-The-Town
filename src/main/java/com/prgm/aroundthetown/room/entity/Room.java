package com.prgm.aroundthetown.room.entity;

import com.prgm.aroundthetown.accommodation.entity.Accommodation;
import com.prgm.aroundthetown.common.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "room")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Room extends BaseEntity {

    @Id
    @Column(name = "room_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long roomId;

    @Column(name = "room_name")
    private String roomName;

    @Column(name = "reservation_notice")
    @Lob
    private String reservationNotice;

    @Column(name = "room_information")
    @Lob
    private String roomInformation;

    @Column(name = "standard_people")
    private int standardPeople;

    @Column(name = "maximum_people")
    private int maximumPeople;

    @Column(name = "price")
    private int price;

    @Column(name = "stock")
    private int stock;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", referencedColumnName = "product_id", nullable = false)
    private Accommodation accommodation;

    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RoomImage> roomImages = new ArrayList<>();

    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RoomReservation> roomReservations = new ArrayList<>();

    @Builder
    public Room(final String roomName,
                final String reservationNotice,
                final String roomInformation,
                final int standardPeople,
                final int maximumPeople,
                final int price,
                final int stock,
                final Accommodation accommodation,
                final List<RoomImage> roomImages,
                final List<RoomReservation> roomReservations) {
        this.roomName = roomName;
        this.reservationNotice = reservationNotice;
        this.roomInformation = roomInformation;
        this.standardPeople = standardPeople;
        this.maximumPeople = maximumPeople;
        this.accommodation = accommodation;
        this.price = price;
        this.stock = stock;
        this.roomImages = roomImages;
        this.roomReservations = roomReservations;
        accommodation.addRoom(this);
    }

    public Long changeInformation(final String roomName,
                                  final String reservationNotice,
                                  final String roomInformation,
                                  final int standardPeople,
                                  final int maximumPeople,
                                  final int price,
                                  final int stock) {
        this.roomName = roomName;
        this.reservationNotice = reservationNotice;
        this.roomInformation = roomInformation;
        this.standardPeople = standardPeople;
        this.maximumPeople = maximumPeople;
        this.price = price;
        this.stock = stock;
        return this.roomId;
    }

    public void addImage(final RoomImage image) {
        if (Objects.isNull(roomImages)) {
            roomImages = new ArrayList<>();
        }
        roomImages.add(image);
    }

    public void addReservation(final RoomReservation roomReservation) {
        if (Objects.isNull(roomReservations)) {
            roomReservations = new ArrayList<>();
        }
        roomReservations.add(roomReservation);
    }

}
