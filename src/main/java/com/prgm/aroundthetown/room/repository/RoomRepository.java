package com.prgm.aroundthetown.room.repository;

import com.prgm.aroundthetown.room.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
