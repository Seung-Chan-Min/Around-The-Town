package com.prgm.aroundthetown.product.room.repository;

import com.prgm.aroundthetown.product.room.entity.RoomReservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomReservationRepository extends JpaRepository<RoomReservation, Long> {
}
