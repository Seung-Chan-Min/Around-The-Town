package com.prgm.aroundthetown.member;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "review_image")
public class ReviewImage {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "review_id")
    private Long reviewId;

    @Column(name = "IMAGE_PATH")
    private String IMAGE_PATH;

}
