package drobczyk.bartlomiej.repo;

import drobczyk.bartlomiej.model.teacher.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TeacherRepo extends JpaRepository<Teacher,Long> {
    boolean existsTeacherByLoginAndEmail(String login, String email);
    boolean existsTeacherByLoginAndPassword(String login, String password);
    Teacher findByLogin(String login);
}