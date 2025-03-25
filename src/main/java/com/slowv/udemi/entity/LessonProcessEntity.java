package com.slowv.udemi.entity;
import com.slowv.udemi.entity.enums.ProcessStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

@Getter
@Setter
@Entity
@Table(name = "lesson_process")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LessonProcessEntity extends AbstractAuditingEntity<Long> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
    @SequenceGenerator(name = "sequence", sequenceName = "lesson_process_seq")
    Long id;

    @Comment("Bài học đăng ký")
    @ManyToOne
    @JoinColumn(name = "lesson_id", referencedColumnName = "id")
    LessonEntity lesson;

    @Comment("Trạng thái của bài học này học viên đã học hay chưa, sẽ được update khi bắt đầu học")
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    ProcessStatus status = ProcessStatus.NOT_STARTED;

    @ManyToOne
    RegisterCourseEntity registerCourse;
}
