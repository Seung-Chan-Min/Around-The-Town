package com.prgm.aroundthetown.room.service;

import com.prgm.aroundthetown.accommodation.entity.Accommodation;
import com.prgm.aroundthetown.accommodation.repository.AccommodationRepository;
import com.prgm.aroundthetown.common.exception.NotFoundException;
import com.prgm.aroundthetown.room.converter.RoomConverter;
import com.prgm.aroundthetown.room.dto.RoomCreateRequestDto;
import com.prgm.aroundthetown.room.dto.RoomCreateResponseDto;
import com.prgm.aroundthetown.room.dto.RoomResponseDto;
import com.prgm.aroundthetown.room.entity.Room;
import com.prgm.aroundthetown.room.entity.RoomReservation;
import com.prgm.aroundthetown.room.repository.RoomRepository;
import com.prgm.aroundthetown.room.repository.RoomReservationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class RoomServiceImpl implements RoomService {

    private final AccommodationRepository accommodationRepository;

    private final RoomRepository roomRepository;

    private final RoomReservationRepository roomReservationRepository;

    private final RoomConverter roomConverter;

    @Override
    @Transactional
    public RoomCreateResponseDto saveRoom(final RoomCreateRequestDto requestCreateRoomDto, final Long productId) {
        final Accommodation accommodation = accommodationRepository.getById(productId);
        final Room room = roomRepository
                .save(roomConverter.requestCreateRoomDtoToEntity(requestCreateRoomDto, accommodation));
        initRoomRemains(room.getStock(), room);

        return roomConverter.entityToResponseCreateDto(room);
    }

    @Override
    @Transactional
    public List<RoomResponseDto> getRoomsByCheckinAndCheckOut(final Long accommodationId, final LocalDate checkIn) {
        final Accommodation accommodation = accommodationRepository
                .findById(accommodationId)
                .orElseThrow(() -> new NotFoundException("?????? ?????? ??????"));
        final List<Room> rooms = accommodation.getRooms();
        final List<RoomResponseDto> roomResponseDtos = new ArrayList<>();

        rooms.forEach(room -> {
            roomReservationRepository
                    .findByRoomAndDates(room, checkIn)
                    .ifPresent(roomReservation -> {
                        if (roomReservation.getRemains() > 0) {
                            roomResponseDtos.add(roomConverter.entityToRoomResponseDto(roomReservation.getRemains(), room, accommodation));
                        }
                    });
        });
        return roomResponseDtos;
    }

    public void initRoomRemains(final int maxStock, final Room room) {
        for (int i = 0; i < 90; i++) {
            final RoomReservation roomReservation = RoomReservation.builder()
                    .dates(LocalDate.now().plusDays(i))
                    .remains(maxStock)
                    .room(room)
                    .build();
            roomReservationRepository.save(roomReservation);
        }
    }

    @Transactional
    @Scheduled(cron = "0 0 0 * * *")
    public void updateRemainsRoom() {//?????? 12??? ?????? ????????? ????????? ??????????????? ???????????? 90???????????? ????????????.
        roomRepository.findAll().forEach(room -> {
            final List<RoomReservation> roomReservations = room.getRoomReservations();
            roomReservations.remove(0);
            roomReservations.add(RoomReservation.builder()
                    .room(room)
                    .remains(room.getStock())
                    .dates(LocalDate.now())
                    .build());
            roomRepository.save(room);
        });
    }
}