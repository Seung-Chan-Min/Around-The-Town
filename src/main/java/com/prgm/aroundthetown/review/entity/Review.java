package com.prgm.aroundthetown.review.entity;

import com.prgm.aroundthetown.common.entity.BaseEntity;
import com.prgm.aroundthetown.member.entity.Member;
import com.prgm.aroundthetown.accommodation.entity.Accommodation;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "review")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SuperBuilder
public class Review extends BaseEntity {

    @Id
    @Column(name = "review_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long reviewId;

    @Column(name = "content")
    @Lob
    private String content;

    @Column(name = "score")
    private int score;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id", referencedColumnName = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", referencedColumnName = "product_id", nullable = false)
    private Accommodation accommodation;

    @OneToMany(mappedBy = "review", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReviewImage> reviewImages;

    /*@Builder
    public Review(final String content, final int score, final Member member, final Accommodation accommodation, final List<ReviewImage> reviewImages) {
        this.content = content;
        this.score = score;
        this.member = member;
        this.accommodation = accommodation;
        this.reviewImages = reviewImages;
        member.addReview(this);
        accommodation.addReview(this);
    }*/

    public void addImage(final ReviewImage image) {
        if (Objects.isNull(reviewImages)) {
            reviewImages = new ArrayList<>();
        }
        reviewImages.add(image);
    }

    public void update(final String content, final int score, final List<ReviewImage> reviewImages) {
        this.content = content;
        this.score = score;
        this.reviewImages = reviewImages;
    }
}
