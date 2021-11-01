package com.prgm.aroundthetown.ticket.repository;

import com.prgm.aroundthetown.host.entity.Host;
import com.prgm.aroundthetown.host.repository.HostRepository;
import com.prgm.aroundthetown.product.Location;
import com.prgm.aroundthetown.product.Region;
import com.prgm.aroundthetown.product.leisure.entity.Leisure;
import com.prgm.aroundthetown.product.leisure.entity.LeisureCategory;
import com.prgm.aroundthetown.product.leisure.repository.LeisureRepository;
import com.prgm.aroundthetown.ticket.entity.Ticket;
import com.prgm.aroundthetown.ticket.entity.TicketImage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest
class TicketImageRepositoryTest {

    @Autowired
    private TicketImageRepository ticketImageRepository;
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private LeisureRepository leisureRepository;
    @Autowired
    private HostRepository hostRepository;

    private Ticket savedTicket;

    @BeforeEach
    void setUp() {
        final Host host = Host.builder()
                .hostName("name")
                .hostEmail("email")
                .hostPhoneNumber("0106666")
                .build();
        final Host savedHost = hostRepository.save(host);

        final Leisure leisure = Leisure.builder()
                .host(savedHost)
                .refundRule("rule")
                .location(Location.builder().build())
                .phoneNumber("phone")
                .businessRegistrationNumber("number")
                .businessAddress("address")
                .businessName("namebu")
                .region(Region.SEOUL)
                .leisureInfomation("info")
                .usecase("usecase")
                .leisureNotice("notice")
                .expirationDate(LocalDateTime.now())
                .category(LeisureCategory.AMUSEMENTPARK)
                .build();
        final Leisure savedLeisure = leisureRepository.save(leisure);

        final Ticket ticket = Ticket.builder()
                .ticketName("2인용")
                .price(25000)
                .leisure(savedLeisure)
                .build();
        savedTicket = ticketRepository.save(ticket);
    }

    @Test
    @DisplayName("ticket을 save 할 수 있다.")
    @Transactional
    void saveTicketTest() {
        final TicketImage image = TicketImage.builder()
                .IMAGE_PATH("path")
                .ticket(savedTicket)
                .build();
        ticketImageRepository.save(image);

        assertThat(ticketImageRepository.findAll().size(), is(1));

        // 연관관계 mapping
        assertThat(ticketRepository.findAll().get(0).getTicketImages().size(), is(1));
    }
}