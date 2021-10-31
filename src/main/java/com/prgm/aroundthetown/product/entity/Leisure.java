package com.prgm.aroundthetown.product.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("LEISURE")
@Entity
public class Leisure extends Product {

    private String leisureInformation;
    private String leisureNotice;
    private String usecase;
    private LocalDateTime expirationStart; // Note : Add
    private LocalDateTime expirationEnd; // Note : Add

    // Todo : Leisure 종류

    @OneToMany(mappedBy = "leisure", fetch = FetchType.LAZY)
    private List<Ticket> tickets;

    public void addTicket(Ticket ticket) {
        ticket.setLeisure(this);
    }

}
