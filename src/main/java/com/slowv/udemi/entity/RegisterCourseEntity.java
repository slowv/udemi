package com.slowv.udemi.entity;


import com.slowv.udemi.entity.enums.RegisterLessonStatus;
import com.slowv.udemi.entity.enums.RegisterType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Comment;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "register_course")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegisterCourseEntity extends AbstractAuditingEntity<Long> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
    @SequenceGenerator(name = "sequence", sequenceName = "register_course_seq")
    Long id;

    @Comment("Email tạm của guest để admin có thể liên hệ")
    @Column(name = "email")
    String email;

    @Comment("Số điện thoại tạm của guest để admin có thể liên hệ")
    @Column(name = "phone", nullable = false)
    String phone;

    @Comment("Tên khóa học")
    @Column(name = "title")
    String title;

    @Comment("Tổng tiền của tất cả các khóa học đã chọn")
    @Column(name = "total_amount")
    BigDecimal totalAmount = BigDecimal.ZERO;

    @Comment("Tổng học phí đã đóng")
    @Column(name = "total_paid_amount")
    BigDecimal totalPaidAmount = BigDecimal.ZERO;

    @Comment("Kiểu đăng ký là bài học lẻ hay là cả khóa học thì giá tổng sẽ khác nhau")
    @Column(name = "register_type")
    @Enumerated(EnumType.STRING)
    RegisterType registerType;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    RegisterLessonStatus status = RegisterLessonStatus.WAITING_APPROVAL;

    @Comment("Hình ảnh học viên chuyển khoản hoặc tiền mặt.")
    @Column(name = "images")
    String images;

    @Comment("Ghi chú người đăng ký muốn gửi kèm đơn đăng ký.")
    @Column(name = "nore", columnDefinition = "TEXT")
    String note;

    @Comment("Khi được approved thì sẽ tạo account student và lưu vào đây")
    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    AccountEntity student;

    @Comment("Khi được approved thì sẽ assign giáo viên dạy")
    @ManyToOne
    @JoinColumn(name = "teacher_id", referencedColumnName = "id")
    AccountEntity teacher;

    @Comment("Tiến trình trạng thái bài học mặc định khi đăng ký đã lưu luôn vào bảng với status là NOT_STARTED")
    @OneToMany(mappedBy = "registerCourse", cascade = CascadeType.ALL)
    List<LessonProcessEntity> lessonProcesses = new ArrayList<>();
}
