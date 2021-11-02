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

@Getter
@Entity
@Table(name = "leisure")
@DiscriminatorValue("leisure")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SuperBuilder
//@SQLDelete(sql = "UPDATE table_leisure SET is_deleted = true WHERE id=?")
//@Where(clause = "is_deleted = false")
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

    @Column(name = "category")
    @Enumerated(EnumType.STRING)
    private LeisureCategory category;

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
        this.category = leisure.getCategory();
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
