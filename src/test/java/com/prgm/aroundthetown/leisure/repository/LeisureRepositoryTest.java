package com.prgm.aroundthetown.product.leisure.repository;

import com.prgm.aroundthetown.host.entity.Host;
import com.prgm.aroundthetown.host.repository.HostRepository;
import com.prgm.aroundthetown.leisure.entity.Leisure;
import com.prgm.aroundthetown.leisure.entity.LeisureCategory;
import com.prgm.aroundthetown.leisure.repository.LeisureRepository;
import com.prgm.aroundthetown.product.entity.Location;
import com.prgm.aroundthetown.product.entity.Region;
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
class LeisureRepositoryTest {

    @Autowired
    private LeisureRepository leisureRepository;
    @Autowired
    private HostRepository hostRepository;

    private Host savedHost;

    @BeforeEach
    void setUp() {
        final Host host = Host.builder()
                .hostName("name")
                .hostEmail("email")
                .hostPhoneNumber("0106666")
                .build();
        savedHost = hostRepository.save(host);
    }

    @Test
    @DisplayName("leisure를 save 할 수 있다.")
    @Transactional
    void saveLeisureTest() {
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
        leisureRepository.save(leisure);

        assertThat(leisureRepository.findAll().size(), is(1));

        // 연관관계 mapping
        assertThat(hostRepository.findAll().get(0).getProducts().size(), is(1));
    }
}