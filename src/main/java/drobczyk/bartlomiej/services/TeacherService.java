package drobczyk.bartlomiej.services;

import drobczyk.bartlomiej.model.teacher.Teacher;
import drobczyk.bartlomiej.repo.TeacherRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeacherService{
    private TeacherRepo teacherRepo;

    @Autowired
    public TeacherService(TeacherRepo teacherRepo) {
        this.teacherRepo = teacherRepo;
    }

    public void saveTeacher(Teacher teacher){
        teacherRepo.save(teacher);
    }

    public Teacher updateTeacher(Teacher teacher){
        return teacherRepo.save(teacher);
    }

}
