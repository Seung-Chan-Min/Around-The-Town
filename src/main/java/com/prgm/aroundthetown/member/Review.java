package com.prgm.aroundthetown.member;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "review")
public class Review {

    @Id
    @Column(name = "review_id")
    private Long reviewId;

    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "accommodation_id")
    private Long accommodationId;

    @Column(name = "content")
    private String content;

    @Column(name = "score")
    private int score;

}
