package com.prgm.aroundthetown.room.entity;

import com.prgm.aroundthetown.common.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "room_reservation")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class RoomReservation extends BaseEntity {

    @Id
    @Column(name = "room_reservation_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "remains", nullable = false)
    private int remains;

    @Column(name = "dates", nullable = false)
    private LocalDateTime dates;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "room_id", referencedColumnName = "room_id", nullable = false)
    private Room room;

    @Builder
    public RoomReservation(final int remains, final LocalDateTime dates, final Room room) {
        this.remains = remains;
        this.dates = dates;
        this.room = room;
        room.addReservation(this);
    }

    // TODO : 스프링 스케줄러로 구현 예정
    // TODO : Date 타입 변경 예정
    // TODO : TestCode 작성 예정
}
