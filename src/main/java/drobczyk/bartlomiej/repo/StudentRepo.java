package drobczyk.bartlomiej.repo;

import drobczyk.bartlomiej.model.Student.Student;
import drobczyk.bartlomiej.model.Teacher.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface StudentRepo extends JpaRepository<Student,Long> {
    @Override
    Optional<Student> findById(Long aLong);
    Set<Student> findAllByTeacher(Teacher teacher);

}
