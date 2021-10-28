package com.prgm.aroundthetown.common;

import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity<T> extends BaseTimeAndDeletedEntity {

    @CreatedBy
    @Column(name = "created_by", updatable = false)
    private T createdBy;

    @LastModifiedBy
    @Column(name = "modified_by")
    private T modifiedBy;

}
