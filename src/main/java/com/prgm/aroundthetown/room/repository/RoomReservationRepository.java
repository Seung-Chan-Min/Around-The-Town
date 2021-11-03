package com.prgm.aroundthetown.room.repository;

import com.prgm.aroundthetown.room.entity.Room;
import com.prgm.aroundthetown.room.entity.RoomReservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface RoomReservationRepository extends JpaRepository<RoomReservation, Long> {

    List<Room> findAllDatesGreaterThanEqualAndDatesLessThanEqualAndRemainsGreaterThan(LocalDateTime dates, LocalDateTime dates2, int remains);
}
