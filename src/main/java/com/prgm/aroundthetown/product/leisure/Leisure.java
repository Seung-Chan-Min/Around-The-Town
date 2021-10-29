package com.prgm.aroundthetown.product.leisure;

import com.prgm.aroundthetown.product.Product;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "leisure")
@DiscriminatorValue("leisure")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Accessors(chain = true)
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

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ticket> ticketList = new ArrayList<>();

    public void addTicket(final Ticket ticket) {
        if (Objects.isNull(ticketList)) {
            ticketList = new ArrayList<>();
        }
        ticketList.add(ticket);
    }

    // host와 연관관계 매핑 예정
}
