package drobczyk.bartlomiej.session;

import drobczyk.bartlomiej.model.Teacher.Teacher;
import drobczyk.bartlomiej.services.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.Optional;

@SessionScope
@Component
public class TeacherSession {
    private Teacher teacher;
    private TeacherService teacherService;

    @Autowired
    public TeacherSession(TeacherService teacherService) {
        this.teacherService = teacherService;
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

    public void refresh(){
        Teacher updatedTeacher = teacherService.updateTeacher(getTeacher());
        setTeacher(updatedTeacher);
    }
}
