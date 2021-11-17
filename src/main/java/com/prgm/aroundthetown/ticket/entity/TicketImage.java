package com.prgm.aroundthetown.ticket.entity;

import com.prgm.aroundthetown.common.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "ticket_image")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class TicketImage extends BaseEntity {

    @Id
    @Column(name = "ticket_image_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "IMAGE_PATH", nullable = false)
    private String IMAGE_PATH;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ticket_id", referencedColumnName = "ticket_id", nullable = false)
    private Ticket ticket;

    @Builder
    public TicketImage(final String IMAGE_PATH, final Ticket ticket) {
        this.IMAGE_PATH = IMAGE_PATH;
        this.ticket = ticket;
        ticket.addImage(this);
    }
}
