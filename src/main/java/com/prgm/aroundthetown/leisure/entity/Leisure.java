package com.prgm.aroundthetown.leisure.entity;

import com.prgm.aroundthetown.product.entity.Product;
import com.prgm.aroundthetown.ticket.entity.Ticket;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@Table(name = "leisure")
@DiscriminatorValue("LEISURE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SuperBuilder
@Entity
public class Leisure extends Product {
    // TODO :: service에서 연관관계 편의 메소드 추가

    @Column(name = "leisure_information")
    @Lob
    private String leisureInformation;

    @Column(name = "usecase")
    @Lob
    private String usecase;

    @Column(name = "leisure_notice")
    @Lob
    private String leisureNotice;

    @Column(name = "expiration_date")
    private LocalDateTime expirationDate;

    @Column(name = "leisure_category")
    @Enumerated(EnumType.STRING)
    private LeisureCategory leisureCategory;

    @OneToMany(mappedBy = "leisure", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Ticket> tickets = new ArrayList<>();

    public void addTicket(final Ticket ticket) {
        if (Objects.isNull(tickets)) {
            tickets = new ArrayList<>();
        }
        tickets.add(ticket);
    }

    public Leisure update(final Leisure leisure) {
        this.leisureInformation = leisure.getLeisureInformation();
        this.usecase = leisure.getUsecase();
        this.leisureNotice = leisure.getLeisureNotice();
        this.expirationDate = leisure.getExpirationDate();
        this.leisureCategory = leisure.getLeisureCategory();
        super.update(
                leisure.getRefundRule(),
                leisure.getPhoneNumber(),
                leisure.getBusinessRegistrationNumber(),
                leisure.getBusinessAddress(),
                leisure.getBusinessName(),
                leisure.getRegion(),
                leisure.getLocation());
        return this;
    }
}
