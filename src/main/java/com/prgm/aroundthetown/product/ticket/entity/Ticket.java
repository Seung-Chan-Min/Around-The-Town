package com.prgm.aroundthetown.product.ticket.entity;

import com.prgm.aroundthetown.common.BaseEntity;
import com.prgm.aroundthetown.product.leisure.entity.Leisure;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "ticket")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Ticket extends BaseEntity {

    @Id
    @Column(name = "ticket_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "ticket_name")
    private String ticketName;

    @Column(name = "price")
    private int price;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", referencedColumnName = "product_id", nullable = false)
    private Leisure leisure;

    @OneToMany(mappedBy = "ticket", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TicketImage> ticketImages;

    @Builder
    public Ticket(final String ticketName, final int price, final Leisure leisure) {
        this.ticketName = ticketName;
        this.price = price;
        this.leisure = leisure;
        leisure.addTicket(this);
    }

    public void addImage(final TicketImage image) {
        if (Objects.isNull(ticketImages)) {
            ticketImages = new ArrayList<>();
        }
        ticketImages.add(image);
    }
}
