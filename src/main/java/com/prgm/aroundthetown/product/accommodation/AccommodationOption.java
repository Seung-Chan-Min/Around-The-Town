package com.prgm.aroundthetown.product.accommodation;

import com.prgm.aroundthetown.common.BaseEntity;
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
    private Long Id;

    @Column(name = "option")
    @Convert(converter = AccommodationOptionCategoryConverter.class)
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
