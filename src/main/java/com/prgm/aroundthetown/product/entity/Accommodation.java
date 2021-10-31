package com.prgm.aroundthetown.product.entity;

import com.prgm.aroundthetown.member.entity.Review;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("ACCOMMODATION")
@Entity
public class Accommodation extends Product{

    private String name;
    private String howToUse;
    private String accommodationNotice;
    private String themeNotice;

//    @Enumerated(EnumType.STRING)
//    private List<ThemeType> themes; // Todo : option enum 배열

    @OneToMany(mappedBy = "accommodation", fetch = FetchType.LAZY)
    @Builder.Default
    private List<Room> rooms = new ArrayList<>();

    @OneToMany(mappedBy = "accommodation", fetch = FetchType.LAZY)
    @Builder.Default
    private List<Review> reviews = new ArrayList<>();

    public void addRoom(Room room) {
        room.setAccommodation(this);
    }

    public void addReview(Review review) {
        review.setAccommodation(this);
    }

}
