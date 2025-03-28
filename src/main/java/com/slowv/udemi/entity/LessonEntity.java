package com.slowv.udemi.entity;

import com.slowv.udemi.entity.enums.CostType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Comment;

import java.io.Serializable;
import java.math.BigDecimal;

@Setter
@Getter
@Entity
@Table(name = "lesson")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LessonEntity extends AbstractAuditingEntity<Long> implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
    @SequenceGenerator(name = "sequence", sequenceName = "lesson_seq")
    Long id;

    @Column(name = "title", nullable = false)
    String title;

    @Column(name = "price", nullable = false)
    BigDecimal price = BigDecimal.ZERO;

    @Comment("Số buổi học")
    @Column(name = "day")
    Integer day = 0;

    @Column(name = "description", columnDefinition = "TEXT")
    String description;

    @Column(name = "cost_type")
    @Enumerated(EnumType.STRING)
    @Comment("Kiểu tính phí theo bài học hoặc theo số buổi")
    CostType costType = CostType.COURSE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    CourseEntity course;
}
