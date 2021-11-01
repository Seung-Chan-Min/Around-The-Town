package com.prgm.aroundthetown.leisure.entity;

import com.prgm.aroundthetown.product.entity.Product;
import com.prgm.aroundthetown.ticket.entity.Ticket;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@DiscriminatorValue("leisure")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SuperBuilder
public class Leisure extends Product {
    // TODO :: service에서 연관관계 편의 메소드 추가

    @Column(name = "leisure_infomation")
    @Lob
    private String leisureInfomation;

    @Column(name = "usecase")
    @Lob
    private String usecase;

    @Column(name = "leisure_notice")
    @Lob
    private String leisureNotice;

    @Column(name = "expiration_date")
    private LocalDateTime expirationDate;

    @Column(name = "category")
    @Convert(converter = LeisureCategoryConverter.class)
    private LeisureCategory category;

    @OneToMany(mappedBy = "leisure", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ticket> tickets = new ArrayList<>();

    public void addTicket(final Ticket ticket) {
        if (Objects.isNull(tickets)) {
            tickets = new ArrayList<>();
        }
        tickets.add(ticket);
    }

    public Leisure update(final String leisureInfomation,
                          final String usecase,
                          final String leisureNotice,
                          final LocalDateTime expirationDate,
                          final LeisureCategory category) {
        this.leisureInfomation = leisureInfomation;
        this.usecase = usecase;
        this.leisureNotice = leisureNotice;
        this.expirationDate = expirationDate;
        this.category = category;
        return this;
    }
}
