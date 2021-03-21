package drobczyk.bartlomiej.repo;

import drobczyk.bartlomiej.model.Student.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepo extends JpaRepository<Student,Long> {
    @Override
    Optional<Student> findById(Long aLong);
}
