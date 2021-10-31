package com.prgm.aroundthetown.product.entity;

import com.prgm.aroundthetown.common.BaseEntity;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class RoomImage extends BaseEntity {
    @Id @GeneratedValue
    private Long id;

    private String imagePath;

}
