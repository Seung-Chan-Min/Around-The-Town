package com.prgm.aroundthetown.room.entity;

import com.prgm.aroundthetown.accommodation.entity.Accommodation;
import com.prgm.aroundthetown.common.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "room")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SuperBuilder
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
