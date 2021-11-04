package com.prgm.aroundthetown.room.repository;

import com.prgm.aroundthetown.room.entity.Room;
import com.prgm.aroundthetown.room.entity.RoomReservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface RoomReservationRepository extends JpaRepository<RoomReservation, Long> {
    Optional<RoomReservation> findByRoomAndDates(Room room, LocalDate checkIn);
}
