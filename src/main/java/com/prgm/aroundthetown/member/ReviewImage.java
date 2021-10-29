package com.prgm.aroundthetown.member;

import com.prgm.aroundthetown.common.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "review_image")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ReviewImage extends BaseEntity {

    @Id
    @Column(name = "review_image_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "IMAGE_PATH")
    private String IMAGE_PATH;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "review_id", referencedColumnName = "review_id", nullable = false)
    private Review review;

    @Builder
    public ReviewImage(final String IMAGE_PATH, final Review review) {
        this.IMAGE_PATH = IMAGE_PATH;
        this.review = review;
        review.addImage(this);
    }
}
