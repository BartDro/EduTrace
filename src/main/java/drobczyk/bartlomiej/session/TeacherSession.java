package drobczyk.bartlomiej.session;

import drobczyk.bartlomiej.model.Teacher.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.Optional;

@SessionScope
@Component
public class TeacherSession {
    private Teacher teacher;

    @Autowired
    public TeacherSession() {
    }
    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public boolean isTeacherLogged(){
        return Optional.ofNullable(teacher).isPresent();
    }
}
