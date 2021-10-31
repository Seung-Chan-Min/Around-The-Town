package com.prgm.aroundthetown.product.accommodation.entity;

import com.prgm.aroundthetown.host.entity.Host;
import com.prgm.aroundthetown.product.Location;
import com.prgm.aroundthetown.product.Product;
import com.prgm.aroundthetown.product.Region;
import com.prgm.aroundthetown.review.entity.Review;
import com.prgm.aroundthetown.room.entity.Room;
import lombok.*;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "accommodation")
@DiscriminatorValue("accommodation")
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Accommodation extends Product {

    @Column(name = "accommodation_name")
    private String accommodationName;

    @Column(name = "accommodation_notice")
    @Lob
    private String accommodationNotice;

    @Column(name = "option_notice")
    @Lob
    private String optionNotice;

    @Column(name = "guide")
    @Lob
    private String guide;

    @Column(name = "category")
    @Convert(converter = AccommodationCategoryConverter.class)
    private AccommodationCategory accommodationCategory;

    @OneToMany(mappedBy = "accommodation", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Room> rooms = new ArrayList<>();

    @OneToMany(mappedBy = "accommodation", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AccommodationImage> images = new ArrayList<>();

    @OneToMany(mappedBy = "accommodation", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "accommodation", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<AccommodationOption> options = new HashSet<>();

    @Builder
    public Accommodation(final Host host,
                         final Long productId,
                         final String refundRule,
                         final Location location,
                         final String phoneNumber,
                         final String businessRegistrationNumber,
                         final String businessAddress,
                         final String businessName,
                         final Region region,
                         final String accommodationName,
                         final String accommodationNotice,
                         final String optionNotice,
                         final String guide,
                         final AccommodationCategory accommodationCategory) {
        super(host, productId, refundRule, location, phoneNumber, businessRegistrationNumber, businessAddress, businessName, region);
        this.accommodationName = accommodationName;
        this.accommodationNotice = accommodationNotice;
        this.optionNotice = optionNotice;
        this.guide = guide;
        this.accommodationCategory = accommodationCategory;
        host.addProduct(this);
    }

    public void addRoom(final Room room) {
        if (Objects.isNull(rooms)) {
            rooms = new ArrayList<>();
        }
        rooms.add(room);
    }

    public void addImage(final AccommodationImage image) {
        if (Objects.isNull(images)) {
            images = new ArrayList<>();
        }
        images.add(image);
    }

    public void addReview(final Review review) {
        if (Objects.isNull(reviews)) {
            reviews = new ArrayList<>();
        }
        reviews.add(review);
    }

    public void addOption(final AccommodationOption accommodationOption) {
        if (Objects.isNull(options)) {
            options = new HashSet<>();
        }
        options.add(accommodationOption);
    }

    public Accommodation update(final String accommodationName,
                                final String accommodationNotice,
                                final String optionNotice,
                                final String guide,
                                final AccommodationCategory accommodationCategory) {
        this.accommodationName = accommodationName;
        this.accommodationNotice = accommodationNotice;
        this.optionNotice = optionNotice;
        this.guide = guide;
        this.accommodationCategory = accommodationCategory;
        return this;
    }
}
