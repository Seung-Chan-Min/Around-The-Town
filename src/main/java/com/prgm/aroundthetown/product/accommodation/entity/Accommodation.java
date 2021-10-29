package com.prgm.aroundthetown.product.accommodation.entity;

import com.prgm.aroundthetown.product.Product;
import com.prgm.aroundthetown.product.room.entity.Room;
import com.prgm.aroundthetown.review.entity.Review;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "accommodation")
@DiscriminatorValue("accommodation")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Accessors(chain = true)
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

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Room> roomList = new ArrayList<>();

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AccommodationImage> imageList = new ArrayList<>();

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Review> reviewList = new ArrayList<>();

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<AccommodationOption> options = new HashSet<>();

    public void addRoom(final Room room) {
        if (Objects.isNull(roomList)) {
            roomList = new ArrayList<>();
        }
        roomList.add(room);
    }

    public void addImage(final AccommodationImage image) {
        if (Objects.isNull(imageList)) {
            imageList = new ArrayList<>();
        }
        imageList.add(image);
    }

    public void addReview(final Review review) {
        if (Objects.isNull(reviewList)) {
            reviewList = new ArrayList<>();
        }
        reviewList.add(review);
    }

    public void addOption(final AccommodationOption accommodationOption) {
        if (Objects.isNull(options)) {
            options = new HashSet<>();
        }
        options.add(accommodationOption);
    }

    // host와 연관관계 매핑 예정
}
