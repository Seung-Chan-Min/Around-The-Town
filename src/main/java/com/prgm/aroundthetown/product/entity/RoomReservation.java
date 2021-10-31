package com.prgm.aroundthetown.product.entity;

import com.prgm.aroundthetown.common.NecessaryBaseEntity;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class RoomReservation extends NecessaryBaseEntity {
    @Id @GeneratedValue
    private Long id;

    // Todo : spring scheduler

}
