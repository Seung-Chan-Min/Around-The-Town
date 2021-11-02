package com.prgm.aroundthetown.ticket.entity;

import com.prgm.aroundthetown.common.entity.BaseEntity;
import com.prgm.aroundthetown.leisure.entity.Leisure;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "ticket")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
//@SQLDelete(sql = "UPDATE table_ticket SET is_deleted = true WHERE id=?")
//@Where(clause = "is_deleted = false")
public class Ticket extends BaseEntity {

    @Id
    @Column(name = "ticket_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "ticket_name")
    private String ticketName;

    @Column(name = "price")
    private Integer price;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", referencedColumnName = "product_id", nullable = false)
    private Leisure leisure;

    @OneToMany(mappedBy = "ticket", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<TicketImage> ticketImages = new ArrayList<>();

    public void addImage(final TicketImage image) {
        if (Objects.isNull(ticketImages)) {
            ticketImages = new ArrayList<>();
        }
        ticketImages.add(image);
    }

    public Ticket update(String ticketName, Integer price) {
        this.ticketName = ticketName;
        this.price = price;
        return this;
    }
}
