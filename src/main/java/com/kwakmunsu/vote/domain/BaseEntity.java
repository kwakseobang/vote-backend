package com.kwakmunsu.vote.domain;


import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@MappedSuperclass // 다른 엔티티들이 BaseTimeEntity를 상속할 경우 BaseTimeEntity의 필드를 컬럼으로 인식
@EntityListeners(AuditingEntityListener.class) // Auditing 기능 포함
public class BaseEntity {
    @CreatedDate
    @Column(name = "created_time", updatable = false)
    private LocalDateTime createdTime;

    @LastModifiedDate
    @Column(name = "updated_time")
    private LocalDateTime updatedTime;
}
