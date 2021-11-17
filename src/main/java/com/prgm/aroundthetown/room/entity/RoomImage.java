package com.prgm.aroundthetown.room.entity;

import com.prgm.aroundthetown.common.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "room_image")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class RoomImage extends BaseEntity {

    @Id
    @Column(name = "room_image_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "IMAGE_PATH")
    private String IMAGE_PATH;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "room_id", referencedColumnName = "room_id", nullable = false)
    private Room room;

    @Builder
    public RoomImage(final String IMAGE_PATH, final Room room) {
        this.IMAGE_PATH = IMAGE_PATH;
        this.room = room;
        room.addImage(this);
    }
}
