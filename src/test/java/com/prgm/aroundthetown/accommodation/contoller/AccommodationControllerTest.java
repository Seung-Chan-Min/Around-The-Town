package com.prgm.aroundthetown.accommodation.contoller;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prgm.aroundthetown.accommodation.dto.AccommodationCreateRequestDto;
import com.prgm.aroundthetown.accommodation.dto.AccommodationOptionDto;
import com.prgm.aroundthetown.accommodation.dto.AccommodationUpdateRequestDto;
import com.prgm.aroundthetown.accommodation.entity.Accommodation;
import com.prgm.aroundthetown.accommodation.entity.AccommodationCategory;
import com.prgm.aroundthetown.accommodation.entity.AccommodationOptionCategory;
import com.prgm.aroundthetown.accommodation.repository.AccommodationRepository;
import com.prgm.aroundthetown.common.ClassLevelTestConfig;
import com.prgm.aroundthetown.host.entity.Host;
import com.prgm.aroundthetown.host.repository.HostRepository;
import com.prgm.aroundthetown.product.Location;
import com.prgm.aroundthetown.product.Region;
import com.prgm.aroundthetown.product.dto.LocationDto;
import com.prgm.aroundthetown.product.dto.ProductCreateRequestDto;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@AutoConfigureMockMvc
@SpringBootTest
class AccommodationControllerTest extends ClassLevelTestConfig {

    @Autowired
    public MockMvc mockMvc;
    Host host;
    Accommodation accommodation;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private HostRepository hostRepository;
    @Autowired
    private AccommodationRepository accommodationRepository;

    @BeforeAll
    void setUp() {
        host = Host.builder()
            .hostName("강민희")
            .hostPhoneNumber("01066669999")
            .hostEmail("kang@naver.com")
            .build();

        accommodation = Accommodation.builder()
            .host(host)
            .accommodationName("accommodation2")
            .accommodationCategory(AccommodationCategory.MOTEL)
            .accommodationNotice("noti")
            .businessAddress("서울")
            .businessName("sample business")
            .optionNotice("option")
            .location(Location.builder()
                .latitude(30.1212)
                .longitude(123.1231231)
                .content("어서오세요")
                .howToVisit("찾아오는길")
                .build())
            .businessRegistrationNumber("122000929391")
            .phoneNumber("01022223333")
            .refundRule("환불규정")
            .region(Region.SEOUL)
            .guide("test")
            .build();

        host.addProduct(accommodation);
        hostRepository.save(host);
    }

    @Test
    @DisplayName("사장님이 숙소(Accommodation)를 등록할 수 있다.")
    @Transactional
    @Rollback(value = false)
    @Order(1)
    void saveAccommodation() throws Exception {
        //given
        final var option1 = AccommodationOptionDto.builder()
            .accommodationOptionCategory(AccommodationOptionCategory.NETFLIX)
            .build();
        final var option2 = AccommodationOptionDto.builder()
            .accommodationOptionCategory(AccommodationOptionCategory.BREAKFAST)
            .build();
        final List<AccommodationOptionDto> accommodationOptions = Arrays.asList(option1, option2);
        final AccommodationCreateRequestDto accommodationCreateRequestDto = AccommodationCreateRequestDto.builder()
            .accommodationName("숙박업체 이름")
            .accommodationNotice("숙박업체 공지")
            .optionNotice("옵션 공지")
            .optionNotice("optionNotice")
            .guide("guide")
            .accommodationCategory(AccommodationCategory.HOTEL)
            .productDto(ProductCreateRequestDto.builder()
                .businessName("미니컴퍼니")
                .refundRule("환불 규정")
                .location(LocationDto.builder()
                    .howToVisit("방문하는 방법")
                    .latitude(31.10000)
                    .longitude(111.11111)
                    .content("버스타고 50분")
                    .build())
                .phoneNumber("01022223333")
                .businessRegistrationNumber("192293293847")
                .businessAddress("경기도 용인시 죽전동")
                .region(Region.GYEONGGI)
                .build())
            .accommodationOptions(accommodationOptions)
            .hostId(host.getId())
            .build();

        //when
        mockMvc.perform(post("/api/v1/hosts/accommodations")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
                .content(objectMapper.writeValueAsString(accommodationCreateRequestDto)))
            .andExpect(status().isCreated())
            .andDo(print());

        //then (마지막에 set(옵션) 넣은거 잘 들어가지는지 확인)
        final List<Accommodation> accommodations = accommodationRepository.findAll();
        final var lastInsertValue = accommodations.get(accommodations.size() - 1).getOptions();
        assertThat(lastInsertValue.size(), is(2));
    }

    @Test
    @DisplayName("사장님이 보유한 숙박업체를 조회할 수 있다.")
    @Rollback(value = false)
    @Transactional
    @Order(2)
    void getAccommodationByHostId() throws Exception {
        //given
        //when
        //then
        mockMvc.perform(get("/api/v1/hosts/{hostId}/accommodations", host.getId())
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print());
    }

    @Test
    @DisplayName("등록된 숙소들중 지역과 카테고리에 맞는 정보만 가져올 수 있다.")
    @Rollback(value = false)
    @Transactional
    @Order(3)
    void getAccommodationByCategoryAndRegion() throws Exception {
        //given
        //when
        //then
        mockMvc.perform(get("/api/v1/accommodations")
                .param("category", String.valueOf(AccommodationCategory.HOTEL))
                .param("region", String.valueOf(Region.GYEONGGI))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print());
    }

    @Test
    @DisplayName("사장님이 숙소(Accommodation)를 수정할 수 있다.")
    @Transactional
    @Rollback(value = false)
    @Order(4)
    void updateAccommodation() throws Exception {
        //given
        final var option1 = AccommodationOptionDto.builder()
            .accommodationOptionCategory(AccommodationOptionCategory.NETFLIX)
            .build();
        final var option2 = AccommodationOptionDto.builder()
            .accommodationOptionCategory(AccommodationOptionCategory.BREAKFAST)
            .build();
        final List<AccommodationOptionDto> accommodationOptions = Arrays.asList(option1, option2);
        final AccommodationUpdateRequestDto accommodationUpdateRequestDto = AccommodationUpdateRequestDto.builder()
            .accommodationName("update 숙박업체 이름")
            .accommodationNotice("update 숙박업체 공지")
            .optionNotice("update 옵션 공지")
            .optionNotice("update optionNotice")
            .guide("update guide")
            .accommodationCategory(AccommodationCategory.HOTEL)
            .productDto(ProductCreateRequestDto.builder()
                .businessName("update 미니컴퍼니")
                .refundRule("update 환불 규정")
                .location(LocationDto.builder()
                    .howToVisit("update 방문하는 방법")
                    .latitude(31.10000)
                    .longitude(111.11111)
                    .content("update 버스타고 50분")
                    .build())
                .phoneNumber("update 01022223333")
                .businessRegistrationNumber("update 192293293847")
                .businessAddress("update 경기도 용인시 죽전동")
                .region(Region.GYEONGGI)
                .build())
            .accommodationOptions(accommodationOptions)
            .hostId(host.getId())
            .build();

        //when
        mockMvc.perform(
                patch("/api/v1/hosts/accommodations/{accommodationId}", accommodation.getProductId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding(StandardCharsets.UTF_8)
                    .content(objectMapper.writeValueAsString(accommodationUpdateRequestDto)))
            .andExpect(status().isOk())
            .andDo(print());

        //then (마지막에 set(옵션) 넣은거 잘 들어가지는지 확인)
        final List<Accommodation> accommodations = accommodationRepository.findAll();
        final var lastInsertValue = accommodations.get(accommodations.size() - 1).getOptions();
        assertThat(lastInsertValue.size(), is(2));
    }

    @Test
    @DisplayName("사장님이 숙소(Accommodation)를 수정할 수 있다.")
    @Transactional
    @Rollback(value = false)
    @Order(4)
    void updateAccommodation() throws Exception {
        //given
        final var option1 = AccommodationOptionDto.builder()
                .accommodationOptionCategory(AccommodationOptionCategory.NETFLIX)
                .build();
        final var option2 = AccommodationOptionDto.builder()
                .accommodationOptionCategory(AccommodationOptionCategory.BREAKFAST)
                .build();
        final List<AccommodationOptionDto> accommodationOptions = Arrays.asList(option1, option2);
        final AccommodationUpdateRequestDto accommodationUpdateRequestDto = AccommodationUpdateRequestDto.builder()
                .accommodationName("update 숙박업체 이름")
                .accommodationNotice("update 숙박업체 공지")
                .optionNotice("update 옵션 공지")
                .optionNotice("update optionNotice")
                .guide("update guide")
                .accommodationCategory(AccommodationCategory.HOTEL)
                .productDto(ProductCreateRequestDto.builder()
                        .businessName("update 미니컴퍼니")
                        .refundRule("update 환불 규정")
                        .location(LocationDto.builder()
                                .howToVisit("update 방문하는 방법")
                                .latitude(31.10000)
                                .longitude(111.11111)
                                .content("update 버스타고 50분")
                                .build())
                        .phoneNumber("update 01022223333")
                        .businessRegistrationNumber("update 192293293847")
                        .businessAddress("update 경기도 용인시 죽전동")
                        .region(Region.GYEONGGI)
                        .build())
                .accommodationOptions(accommodationOptions)
                .hostId(host.getId())
                .build();

        //when
        mockMvc.perform(patch("/api/v1/hosts/accommodations/{accommodationId}", accommodation.getProductId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(objectMapper.writeValueAsString(accommodationUpdateRequestDto)))
                .andExpect(status().isOk())
                .andDo(print());

        //then (마지막에 set(옵션) 넣은거 잘 들어가지는지 확인)
        final List<Accommodation> accommodations = accommodationRepository.findAll();
        final var lastInsertValue = accommodations.get(accommodations.size() - 1).getOptions();
        assertThat(lastInsertValue.size(), is(2));
    }

    @Test
    @DisplayName("등록된 숙소들 전부 확인 가능하다.")
    @Rollback(value = false)
    @Transactional
    @Order(5)
    void getAccommodations() throws Exception {
        //given
        //when
        //then
        mockMvc.perform(get("/api/v1/accommodation-all")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print());
    }

    @Test
    @DisplayName("삭제 시 soft delete 가 된다.")
    @Transactional
    @Order(6)
    @Rollback(value = false)
    void deleteById() throws Exception {
        final Long accommodationId = accommodationRepository.findAll().get(0).getProductId();
        //given
        mockMvc.perform(
                delete("/api/v1/hosts/{hostId}/accommodations/{accommodationId}", host.getId(),
                    accommodationId)
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print());
        //when

        //then
        assertThat(accommodationRepository.findAll().get(0).getIsDeleted(), is(true));

    }


}