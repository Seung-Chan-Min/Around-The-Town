package com.prgm.aroundthetown.room.entity;

import com.prgm.aroundthetown.common.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

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
    private Date dates;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "room_id", referencedColumnName = "room_id", nullable = false)
    private Room room;

    // 스프링 스케줄러로 구현 예정
    // 연관관계 편의 메소드 예정
    // Date 타입 변경 예정
    // TestCode 작성 예정
}
