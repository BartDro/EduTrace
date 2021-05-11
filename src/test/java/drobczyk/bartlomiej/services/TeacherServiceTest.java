package drobczyk.bartlomiej.services;

import drobczyk.bartlomiej.model.teacher.Teacher;
import drobczyk.bartlomiej.repo.RoleRepo;
import drobczyk.bartlomiej.repo.TeacherRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class TeacherServiceTest {
    @Autowired
    private TeacherRepo teacherRepo;

    @Test
    void shouldCheckIfTeacherExists() {
        //given
        Teacher testTeacher = new Teacher("admin","admin@op.pl","hardPass",
                LocalDateTime.now(),new HashSet<>());
        teacherRepo.save(testTeacher);

        //when
        boolean isExist =  teacherRepo.existsTeacherByLoginAndEmail("admin","admin@op.pl");

        //then
        assertThat(isExist).isTrue();
    }
}