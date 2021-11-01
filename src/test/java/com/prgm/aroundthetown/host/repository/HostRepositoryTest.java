package com.prgm.aroundthetown.host.repository;

import com.prgm.aroundthetown.host.entity.Host;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest
class HostRepositoryTest {

    @Autowired
    private HostRepository hostRepository;

    @Test
    @DisplayName("host가 save 될 수 있다.")
    @Transactional
    void hostSaveTest() {
        final Host host = Host.builder()
                .hostName("name")
                .hostEmail("email")
                .hostPhoneNumber("0106666")
                .build();
        hostRepository.save(host);

        assertThat(hostRepository.findAll().size(), is(1));
    }
}