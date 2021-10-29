package com.prgm.aroundthetown.product.room.entity;

import com.prgm.aroundthetown.common.BaseEntity;
import com.prgm.aroundthetown.product.accommodation.entity.Accommodation;
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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", referencedColumnName = "product_id", nullable = false)
    private Accommodation accommodation;

    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RoomImage> roomImages = new ArrayList<>();

    @Builder
    public Room(final String roomName,
                final String reservationNotice,
                final String roomInformation,
                final int standardPeople,
                final int maximumPeople,
                final int price,
                final Accommodation accommodation) {
        this.roomName = roomName;
        this.reservationNotice = reservationNotice;
        this.roomInformation = roomInformation;
        this.standardPeople = standardPeople;
        this.maximumPeople = maximumPeople;
        this.accommodation = accommodation;
        this.price = price;
        accommodation.addRoom(this);
    }

    public Long changeInformation(final String roomName,
                                  final String reservationNotice,
                                  final String roomInformation,
                                  final int standardPeople,
                                  final int maximumPeople,
                                  final int price) {
        this.roomName = roomName;
        this.reservationNotice = reservationNotice;
        this.roomInformation = roomInformation;
        this.standardPeople = standardPeople;
        this.maximumPeople = maximumPeople;
        this.price = price;
        return this.roomId;
    }

    public void addImage(final RoomImage image) {
        if (Objects.isNull(roomImages)) {
            roomImages = new ArrayList<>();
        }
        roomImages.add(image);
    }

}
