package drobczyk.bartlomiej.services;

import drobczyk.bartlomiej.model.Student.Student;
import drobczyk.bartlomiej.model.Teacher.Teacher;
import drobczyk.bartlomiej.repo.TeacherRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;

@Service
public class LogService {
    private TeacherRepo teacherRepo;

    public LogService(TeacherRepo teacherRepo) {
        this.teacherRepo = teacherRepo;
    }

    public boolean registerTeacher(String login, String mail,
                                   String password, String passwordConfirm) {
        if (password.equals(passwordConfirm) && isSuchTeacherAlreadyExists(login, mail)) {
            Teacher regiseredTeacher = new Teacher(login, mail, password, LocalDateTime.now(), new HashSet<>());
            teacherRepo.save(regiseredTeacher);
            return true;
        }
        return false;
    }

    private boolean isSuchTeacherAlreadyExists(String login, String mail) {
        return !teacherRepo.existsTeacherByLoginAndEmail(login, mail);
    }

    public boolean logTeacher(String login, String password) {
        return teacherRepo.existsTeacherByLoginAndPassword(login, password);
    }
}


//<a href="#" >
//                        <img src="../static/img/plus-circle-solid.svg" class="student-add-button"></img>
//                    </a>