package com.prgm.aroundthetown.member.entity;

import com.prgm.aroundthetown.common.NecessaryBaseEntity;
import com.prgm.aroundthetown.product.entity.Accommodation;
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
public class Review extends NecessaryBaseEntity {
    @Id @GeneratedValue
    private Long id;

    // Todo : Validation
    private String content;
    private Integer score;

    // Todo : Review Image

    @ManyToOne(fetch = FetchType.LAZY) // Note : Add
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accommodation_id")
    private Accommodation accommodation;

    public void setMember(Member member) { // 연관관계 편의 메서드
        if (Objects.nonNull(this.member)) {
            this.member.getReviews().remove(this);
        }
        this.member = member;
        member.getReviews().add(this);
    }

    public void setAccommodation(Accommodation accommodation) { // 연관관계 편의 메서드
        if (Objects.nonNull(this.accommodation)) {
            this.accommodation.getReviews().remove(this);
        }
        this.accommodation = accommodation;
        accommodation.getReviews().add(this);
    }

}
