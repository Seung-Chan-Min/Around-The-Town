package com.prgm.aroundthetown.product.entity;

import com.prgm.aroundthetown.common.NecessaryBaseEntity;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
public class Room extends NecessaryBaseEntity {
    @Id @GeneratedValue
    private Long id;

    // Todo : validation
    private String name;
    private String reservationNotice;
    private String roomInformation;
    private Integer price;
    private Integer standardPeople;
    private Integer maximumPeople;
    private Integer maxStock;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accommodation_id")
    private Accommodation accommodation;

    public void setAccommodation(Accommodation accommodation) { // 연관관계 편의 메서드
        if (Objects.nonNull(this.accommodation)) {
            this.accommodation.getRooms().remove(this);
        }
        this.accommodation = accommodation;
        accommodation.getRooms().add(this);
    }

}
