package com.slowv.udemi.migrate;

import com.slowv.udemi.entity.AccountEntity;
import com.slowv.udemi.entity.AccountInfoEntity;
import com.slowv.udemi.entity.RoleEntity;
import com.slowv.udemi.entity.enums.ERole;
import com.slowv.udemi.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AccountInit implements CommandLineRunner {

    private final AccountRepository accountRepository;

    @Override
    public void run(final String... args) throws Exception {
        if (accountRepository.count() == 0) {
            final var teacher = new AccountEntity();
            final var student = new AccountEntity();
            teacher.setEmail("teacher@gmail.com");
            student.setEmail("student@gmail.com");
            teacher.setPassword("viet1998");
            student.setPassword("viet1998");

            teacher.addRole(new RoleEntity(ERole.TEACHER));
            student.addRole(new RoleEntity(ERole.STUDENT));

            final var teacherInfo = new AccountInfoEntity();
            final var studentInfo = new AccountInfoEntity();

            teacherInfo.setFirstName("Trịnh");
            teacherInfo.setLastName("Việt");
            teacherInfo.setPhone("0349555602");
            teacherInfo.setIntroduce("Đẹp trai");

            studentInfo.setFirstName("Phạm");
            studentInfo.setLastName("Quí");
            studentInfo.setPhone("0349111602");
            studentInfo.setIntroduce("Khoai to");

            teacher.setAccountInfo(teacherInfo);
            student.setAccountInfo(studentInfo);

            accountRepository.saveAll(List.of(teacher, student));
        }
    }
}
