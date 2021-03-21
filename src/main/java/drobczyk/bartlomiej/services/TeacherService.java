package drobczyk.bartlomiej.services;

import drobczyk.bartlomiej.model.Teacher.Teacher;
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
}