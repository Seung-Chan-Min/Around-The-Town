package com.prgm.aroundthetown.accommodation.entity;

import com.prgm.aroundthetown.product.entity.Product;
import com.prgm.aroundthetown.review.entity.Review;
import com.prgm.aroundthetown.room.entity.Room;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.*;

@Entity
@DiscriminatorValue("accommodation")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SuperBuilder
@SQLDelete(sql = "UPDATE product SET is_deleted = true WHERE product_id=?")
@Where(clause = "is_deleted = false")
@DynamicInsert
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
    @Enumerated(value = EnumType.STRING)
    private AccommodationCategory accommodationCategory;

    @OneToMany(mappedBy = "accommodation", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Room> rooms = new ArrayList<>();

    @OneToMany(mappedBy = "accommodation", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AccommodationImage> images = new ArrayList<>();

    @OneToMany(mappedBy = "accommodation", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "accommodation", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<AccommodationOption> options = new HashSet<>();

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
