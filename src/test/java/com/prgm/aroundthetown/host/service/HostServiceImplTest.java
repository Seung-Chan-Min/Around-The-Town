package com.prgm.aroundthetown.host.service;

import com.prgm.aroundthetown.host.dto.HostCreateDto;
import com.prgm.aroundthetown.host.dto.HostUpdateDto;
import com.prgm.aroundthetown.host.entity.Host;
import com.prgm.aroundthetown.host.repository.HostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest
class HostServiceImplTest {

    @Autowired
    private HostServiceImpl hostServiceImpl;
    @Autowired
    private HostRepository hostRepository;

    private Long setupHostId;

    @BeforeEach
    void setUp() {
        final Host host1 = Host.builder()
                .hostName("관리자")
                .hostEmail("test@naver.com")
                .hostPhoneNumber("01011112222")
                .build();
        setupHostId = hostRepository.save(host1).getId();
    }

    @Test
    @DisplayName("Create를 할 수 있다.")
    @Transactional
    void testCreate() {
        // Given
        final HostCreateDto dto = HostCreateDto.builder()
                .hostName("최승은")
                .hostEmail("g787@naver.com")
                .hostPhoneNumber("01012345678")
                .build();

        // When
        hostServiceImpl.createHost(dto);

        // Then
        assertThat(hostRepository.findAll().size(), is(2));
        assertThat(hostRepository.findAll().get(1).getHostName(), is("최승은"));
    }

    @Test
    @DisplayName("FindById를 할 수 있다.")
    @Transactional
    void testFindById() {
        assertThat(hostServiceImpl.findById(setupHostId).getHostName(), is("관리자"));
    }

    @Test
    @DisplayName("Update를 할 수 있다.")
    @Transactional
    void testUpdate() {
        // Given
        final HostUpdateDto dto = HostUpdateDto.builder()
                .id(setupHostId)
                .hostName("바뀐이름")
                .hostEmail("g787@naver.com")
                .hostPhoneNumber("01012345678")
                .build();

        // When
        hostServiceImpl.updateHost(dto);

        // Then
        final Host updatedEntity = hostRepository.findAll().get(0);
        assertThat(updatedEntity.getId(), is(setupHostId));
        assertThat(updatedEntity.getHostName(), is("바뀐이름"));
    }

    @Test
    @DisplayName("Delete를 할 수 있다.")
    @Transactional
    void testDelete() {
        assertThat(hostRepository.findById(setupHostId).get().getIsDeleted(), is(false));
        hostServiceImpl.deleteHost(setupHostId);
        assertThat(hostRepository.findById(setupHostId).get().getIsDeleted(), is(true));
    }
}