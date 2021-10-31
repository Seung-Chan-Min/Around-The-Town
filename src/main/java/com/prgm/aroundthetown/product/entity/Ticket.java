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
public class Ticket extends NecessaryBaseEntity {
    @Id @GeneratedValue
    private Long id;

    private String name;
    private Integer price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "leisure_id")
    private Leisure leisure;

    public void setLeisure(Leisure leisure) { // 연관관계 편의 메서드
        if (Objects.nonNull(this.leisure)) {
            this.leisure.getTickets().remove(this);
        }
        this.leisure = leisure;
        leisure.getTickets().add(this);
    }

}
