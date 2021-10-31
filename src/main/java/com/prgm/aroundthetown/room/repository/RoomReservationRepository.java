package com.prgm.aroundthetown.room.repository;

import com.prgm.aroundthetown.room.entity.RoomReservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomReservationRepository extends JpaRepository<RoomReservation, Long> {
}
