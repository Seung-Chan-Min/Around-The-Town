package com.prgm.aroundthetown.room.service;

import com.prgm.aroundthetown.product.accommodation.entity.Accommodation;
import com.prgm.aroundthetown.product.accommodation.repository.AccommodationRepository;
import com.prgm.aroundthetown.room.converter.RoomConverter;
import com.prgm.aroundthetown.room.dto.RequestCreateRoomDto;
import com.prgm.aroundthetown.room.dto.ResponseCreateRoomDto;
import com.prgm.aroundthetown.room.entity.Room;
import com.prgm.aroundthetown.room.entity.RoomReservation;
import com.prgm.aroundthetown.room.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {
    private final AccommodationRepository accommodationRepository;
    private final RoomRepository roomRepository;
    private final RoomConverter roomConverter;

    @Override
    @Transactional
    public ResponseCreateRoomDto saveRoom(final RequestCreateRoomDto requestCreateRoomDto, final Long productId) {
        final Accommodation accommodation = accommodationRepository.getById(productId);
        final Room room = roomRepository
                .save(roomConverter.requestCreateRoomDtoToEntity(requestCreateRoomDto, accommodation));

        return roomConverter.entityToResponseCreateDto(room);
    }

    @Transactional
    @Scheduled(cron = "0 0 0 * * *")
    public void updateRemainsRoom() {//매일 12시 마다 보유한 방들을 현재시간을 기준으로 90일동안만 관리한다.
        roomRepository.findAll().forEach(room -> {
            final List<RoomReservation> roomReservations = room.getRoomReservations();
            roomReservations.remove(0);
            roomReservations.add(RoomReservation.builder()
                    .room(room)
                    .remains(room.getStock())
                    .dates(LocalDateTime.now())
                    .build());
            roomRepository.save(room);
        });
    }
}