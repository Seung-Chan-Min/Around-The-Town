package com.prgm.aroundthetown.product.leisure.entity;

import com.prgm.aroundthetown.host.entity.Host;
import com.prgm.aroundthetown.product.Location;
import com.prgm.aroundthetown.product.Product;
import com.prgm.aroundthetown.product.Region;
import com.prgm.aroundthetown.ticket.entity.Ticket;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "leisure")
@DiscriminatorValue("leisure")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Leisure extends Product {

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

    @Builder
    public Leisure(final Host host,
                   final Long productId,
                   final String refundRule,
                   final Location location,
                   final String phoneNumber,
                   final String businessRegistrationNumber,
                   final String businessAddress,
                   final String businessName,
                   final Region region,
                   final String leisureInfomation,
                   final String usecase,
                   final String leisureNotice,
                   final LocalDateTime expirationDate,
                   final LeisureCategory category) {
        super(host, productId, refundRule, location, phoneNumber, businessRegistrationNumber, businessAddress, businessName, region);
        this.leisureInfomation = leisureInfomation;
        this.usecase = usecase;
        this.leisureNotice = leisureNotice;
        this.expirationDate = expirationDate;
        this.category = category;
        host.addProduct(this);
    }

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
