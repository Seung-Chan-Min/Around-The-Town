package com.prgm.aroundthetown.accommodation.entity;

import com.prgm.aroundthetown.common.entity.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@Table(name = "accommodation_image")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SuperBuilder
public class AccommodationImage extends BaseEntity {

    @Id
    @Column(name = "accommodation_image_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "IMAGE_PATH", nullable = false)
    private String IMAGE_PATH;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", referencedColumnName = "product_id", nullable = false)
    private Accommodation accommodation;

    @Builder
    public AccommodationImage(final String IMAGE_PATH, final Accommodation accommodation) {
        this.IMAGE_PATH = IMAGE_PATH;
        this.accommodation = accommodation;
        accommodation.addImage(this);
    }
}
