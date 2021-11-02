package com.prgm.aroundthetown.accommodation.entity;

import com.prgm.aroundthetown.common.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "accommodation_option")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class AccommodationOption extends BaseEntity {

    @Id
    @Column(name = "accommodation_option_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    @Column(name = "option")
    @Enumerated(EnumType.STRING)
//    @Convert(converter = AccommodationOptionCategoryConverter.class)
    private AccommodationOptionCategory option;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", referencedColumnName = "product_id", nullable = false)
    private Accommodation accommodation;

    @Builder
    public AccommodationOption(final AccommodationOptionCategory option, final Accommodation accommodation) {
        this.option = option;
        this.accommodation = accommodation;
        accommodation.addOption(this);
    }

}
