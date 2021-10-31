package com.prgm.aroundthetown.common;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class NecessaryBaseEntity {

    // Note : 엔티티가 생성되어 저장될 때 시간이 자동 저장됨
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    // Note : 조회한 Entity 값을 변경할 때 시간이 자동 저장됨
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Column(name = "deleted", columnDefinition = "Bit(1) default false")
    private boolean isDeleted = false;

}
